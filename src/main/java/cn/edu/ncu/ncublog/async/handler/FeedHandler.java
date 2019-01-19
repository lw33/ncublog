package cn.edu.ncu.ncublog.async.handler;

import cn.edu.ncu.ncublog.async.EventHandler;
import cn.edu.ncu.ncublog.async.EventModel;
import cn.edu.ncu.ncublog.async.EventType;
import cn.edu.ncu.ncublog.model.Blog;
import cn.edu.ncu.ncublog.model.EntityType;
import cn.edu.ncu.ncublog.model.Feed;
import cn.edu.ncu.ncublog.model.User;
import cn.edu.ncu.ncublog.service.*;
import cn.edu.ncu.ncublog.util.RedisKey;
import cn.edu.ncu.ncublog.util.RedisUtil;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Author lw
 * @Date 2018-09-04 12:52:50
 **/
@Component
public class FeedHandler implements EventHandler {
    private static final Logger logger = LoggerFactory.getLogger(FeedHandler.class);
    @Autowired
    private UserService userService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private FeedService feedService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private FollowService followService;


    @Override
    public void doHandler(EventModel eventModel) {

        try {

            Feed feed = new Feed();
            feed.setCreatedDate(LocalDateTime.now());
            feed.setType(eventModel.getEventType().getValue());
            feed.setUserId(eventModel.getActorId());
            feed.setData(buildFeedData(eventModel));
            int i = feedService.addFeed(feed);
            if (i > 0) {
                logger.info("消费动态信息事件成功...");
            } else {
                logger.error("消费动态信息事件失败...");
            }

            List<Integer> followersId = followService.getFollowersId(EntityType.ENTITY_USER, eventModel.getActorId(), 0, Integer.MAX_VALUE);

            if (followersId != null && followersId.size() > 0) {
                followersId.forEach(followerId -> {
                    String feedsKey = RedisKey.getFeedsKey(followerId);
                    redisUtil.lpush(feedsKey, String.valueOf(feed.getId()));
                });
            }


        } catch (Exception e) {
            logger.error("消费动态信息事件失败...", e.getMessage());
        }

    }

    @Override
    public List<EventType> getInteresEventTypes() {
        return Arrays.asList(EventType.FOLLOW, EventType.LIKE, EventType.COMMENT, EventType.ADD_BLOG);
    }

    private String buildFeedData(EventModel eventModel) {

        Map<String, String> feedData = new HashMap<>();

        User user = userService.getUserById(eventModel.getActorId());
        feedData.put("userHead", user.getHeadUrl());
        feedData.put("userName", user.getName());
        feedData.put("userId", String.valueOf(user.getId()));
        switch (eventModel.getEventType()) {
            case COMMENT:
                Blog blogById = blogService.getBlogById(eventModel.getEntityId());
                feedData.put("blogTitle", blogById.getTitle());
                feedData.put("blogId", String.valueOf(blogById.getId()));
                break;

            case FOLLOW:

                switch (eventModel.getEntityType()) {
                    case EntityType.ENTITY_USER:

                        //feedData.put("")

                        break;

                    case EntityType.ENTITY_BLOG:

                        break;
                }

                break;

            case LIKE:
                feedData.put("blogId", eventModel.get("blogId"));
                break;

            case ADD_BLOG:
                Blog addBlog = blogService.getBlogById(eventModel.getEntityId());
                feedData.put("blogTitle", addBlog.getTitle());
                feedData.put("blogId", String.valueOf(addBlog.getId()));
                break;
        }

        return JSON.toJSONString(feedData);
    }
}
