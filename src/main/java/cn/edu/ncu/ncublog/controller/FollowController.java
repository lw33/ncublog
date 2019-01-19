package cn.edu.ncu.ncublog.controller;

import cn.edu.ncu.ncublog.async.EventModel;
import cn.edu.ncu.ncublog.async.EventProducer;
import cn.edu.ncu.ncublog.async.EventType;
import cn.edu.ncu.ncublog.model.EntityType;
import cn.edu.ncu.ncublog.model.HostHolder;
import cn.edu.ncu.ncublog.service.FollowService;
import cn.edu.ncu.ncublog.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author lw
 * @Date 2018-09-03 22:35:55
 **/

@Controller
public class FollowController {
    private static final Logger logger = LoggerFactory.getLogger(FollowController.class);


    @Autowired
    private FollowService followService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private EventProducer eventProducer;

    @RequestMapping(value = "/followUser", method = RequestMethod.POST)
    @ResponseBody
    public String followUser(@RequestParam("userId") int userId) {


        boolean follow = followService.follow(hostHolder.getUser().getId(), EntityType.ENTITY_USER, userId);

        eventProducer.fireHandler(new EventModel().setEntityOwnerId(userId)
                .setActorId(hostHolder.getUser().getId())
                .setEventType(EventType.FOLLOW)
        );


        return CommonUtil.getJSONString(follow ? 0 : 1, String.valueOf(followService.getFollowerCount(userId, EntityType.ENTITY_USER)));
    }

    @RequestMapping(value = "/unfollowUser", method = RequestMethod.POST)
    @ResponseBody
    public String unfollowUser(@RequestParam("userId") int userId) {

        boolean follow = followService.unfollow(hostHolder.getUser().getId(), EntityType.ENTITY_USER, userId);
        return CommonUtil.getJSONString(follow ? 0 : 1, String.valueOf(followService.getFollowerCount(EntityType.ENTITY_BLOG, userId)));
    }

    @RequestMapping(value = "/followBlog", method = RequestMethod.POST)
    @ResponseBody
    public String followBlog(@RequestParam("blogId") int blogId) {

        boolean follow = followService.follow(hostHolder.getUser().getId(), EntityType.ENTITY_BLOG, blogId);

        Map<String, Object> info = new HashMap<>();
        info.put("headUrl", hostHolder.getUser().getHeadUrl());
        info.put("name", hostHolder.getUser().getName());
        info.put("id", hostHolder.getUser().getId());
        info.put("count", followService.getFollowerCount(EntityType.ENTITY_BLOG, blogId));
        return CommonUtil.getJSONString(follow ? 0 : 1, info);
    }

    @RequestMapping(value = "/unfollowBlog", method = RequestMethod.POST)
    @ResponseBody
    public String unfollowBlog(@RequestParam("blogId") int blogId) {

        boolean follow = followService.unfollow(hostHolder.getUser().getId(), EntityType.ENTITY_BLOG, blogId);
        Map<String, Object> info = new HashMap<>();
        info.put("id", hostHolder.getUser().getId());
        info.put("count", followService.getFollowerCount(EntityType.ENTITY_BLOG, blogId));
        return CommonUtil.getJSONString(follow ? 0 : 1, info);

    }




}
