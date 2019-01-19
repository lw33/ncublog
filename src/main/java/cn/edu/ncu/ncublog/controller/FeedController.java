package cn.edu.ncu.ncublog.controller;

import cn.edu.ncu.ncublog.model.EntityType;
import cn.edu.ncu.ncublog.model.Feed;
import cn.edu.ncu.ncublog.model.HostHolder;
import cn.edu.ncu.ncublog.model.User;
import cn.edu.ncu.ncublog.service.FeedService;
import cn.edu.ncu.ncublog.service.FollowService;
import cn.edu.ncu.ncublog.util.RedisKey;
import cn.edu.ncu.ncublog.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author lw
 * @Date 2018-09-04 14:04:38
 **/

@Controller
public class FeedController {
    private static final Logger logger = LoggerFactory.getLogger(FeedController.class);

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private FeedService feedService;

    @Autowired
    private FollowService followService;

    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping("/pullfeeds")
    public String pullfeeds(Model model, @RequestParam(value = "offset", required = false, defaultValue = "0") int offset, @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {

        try {


            List<Integer> followeesId = followService.getFolloweesId(EntityType.ENTITY_USER, hostHolder.getUser().getId(), 0, Integer.MAX_VALUE);
            if (followeesId == null || followeesId.size() == 0) {
                return "feeds";
            }

            List<Feed> feeds = feedService.getLatestFeedsByUserIds(followeesId, offset, limit);
            model.addAttribute("feeds", feeds);
        } catch (Exception e) {
            logger.error("拉去动态失败...", e.getMessage());

        }
        return "feeds";
    }

    @RequestMapping("/pushfeeds")
    public String pushfeeds(Model model, @RequestParam(value = "offset", required = false, defaultValue = "0") int offset, @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {

        try {


            User user = hostHolder.getUser();
            String feedsKey = RedisKey.getFeedsKey(user.getId());

            List<String> lrange = redisUtil.lrange(feedsKey, offset, offset + limit);
            List<Feed> feeds = new ArrayList<>();
            lrange.forEach(feedId -> {
                Feed feed = feedService.getFeedById(Integer.parseInt(feedId));
                feeds.add(feed);
            });

            model.addAttribute("feeds", feeds);
        } catch (Exception e) {
            logger.error("推送动态失败...", e.getMessage());
        }

        return "feeds";
    }
}
