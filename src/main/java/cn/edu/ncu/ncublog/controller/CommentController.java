package cn.edu.ncu.ncublog.controller;

import cn.edu.ncu.ncublog.async.EventModel;
import cn.edu.ncu.ncublog.async.EventProducer;
import cn.edu.ncu.ncublog.async.EventType;
import cn.edu.ncu.ncublog.model.Comment;
import cn.edu.ncu.ncublog.model.EntityType;
import cn.edu.ncu.ncublog.model.HostHolder;
import cn.edu.ncu.ncublog.service.BlogService;
import cn.edu.ncu.ncublog.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDateTime;

/**
 * @Author lw
 * @Date 2018-09-02 18:32:17
 **/

@Controller
@RequestMapping("/comment")
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private CommentService commentService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private EventProducer eventProducer;

    @Autowired
    private BlogService blogService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addComment(int blogId, String content) {


        try {
            Comment comment = new Comment();
            comment.setEntityId(blogId);
            comment.setEntityType(EntityType.ENTITY_BLOG);
            comment.setContent(content);
            comment.setCreatedDate(LocalDateTime.now());
            comment.setUserId(hostHolder.getUser().getId());
            commentService.addComment(comment);


            eventProducer.fireHandler(new EventModel().setEntityType(EntityType.ENTITY_BLOG)
                    .setEntityId(blogId)
                    .setActorId(hostHolder.getUser().getId())
                    .setEventType(EventType.COMMENT)
                    .setEntityOwnerId(blogService.getUserIdById(blogId))

            );

        } catch (Exception e) {
            logger.error("添加评论失败", e.getMessage());
        }
        return "redirect:/blog/" + blogId;
    }


    @RequestMapping("/delete/{id}")
    public String deleteComment(@PathVariable("id") Integer id) {

        Comment comment = null;
        try {
            comment = commentService.getCommentById(id);
            if (comment == null) {
                return "redirect:/";
            }
            commentService.deleteComment(id);
        } catch (Exception e) {
            logger.error("删除失败", e.getMessage());
            return "redirect:/";
        }
        return "redirect:/blog/" + comment.getEntityId();
    }


}
