package cn.edu.ncu.ncublog.controller;

import cn.edu.ncu.ncublog.async.EventModel;
import cn.edu.ncu.ncublog.async.EventProducer;
import cn.edu.ncu.ncublog.async.EventType;
import cn.edu.ncu.ncublog.model.Comment;
import cn.edu.ncu.ncublog.model.EntityType;
import cn.edu.ncu.ncublog.model.HostHolder;
import cn.edu.ncu.ncublog.service.CommentService;
import cn.edu.ncu.ncublog.service.LikeService;
import cn.edu.ncu.ncublog.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author lw
 * @Date 2018-09-03 16:00:30
 **/
@Controller
public class LikeController {
    private static final Logger logger = LoggerFactory.getLogger(LikeController.class);

    @Autowired
    private LikeService likeService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private EventProducer eventProducer;

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/like", method = RequestMethod.POST)
    @ResponseBody
    public String like(@RequestParam("commentId") int commentId) {

        long likeCount = 0;
        try {

            Comment comment = commentService.getCommentById(commentId);

            likeCount = likeService.like(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, commentId);

            eventProducer.fireHandler(
                    new EventModel().setEventType(EventType.LIKE)
                            .setActorId(hostHolder.getUser().getId())
                            .setEntityOwnerId(comment.getUserId())
                            .setEntityId(comment.getId())
                            .setEntityType(EntityType.ENTITY_COMMENT)
                            .set("blogId", String.valueOf(comment.getEntityId()))
            );

            return CommonUtil.getJSONString(0, String.valueOf(likeCount));
        } catch (Exception e) {
            logger.error("点赞失败", e.getMessage());
            return CommonUtil.getJSONString(1, String.valueOf(likeCount));
        }
    }

    @RequestMapping(value = "/dislike", method = RequestMethod.POST)
    @ResponseBody
    public String dislike(@RequestParam("commentId") int commentId) {
        long likeCount = 0;
        try {
            likeCount = likeService.dislike(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, commentId);
            return CommonUtil.getJSONString(0, String.valueOf(likeCount));
        } catch (Exception e) {
            logger.error("取消点赞失败", e.getMessage());
            return CommonUtil.getJSONString(1, String.valueOf(likeCount));
        }
    }

}
