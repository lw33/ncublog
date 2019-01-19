package cn.edu.ncu.ncublog.controller;

import cn.edu.ncu.ncublog.async.EventModel;
import cn.edu.ncu.ncublog.async.EventProducer;
import cn.edu.ncu.ncublog.async.EventType;
import cn.edu.ncu.ncublog.model.*;
import cn.edu.ncu.ncublog.service.*;
import cn.edu.ncu.ncublog.util.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author lw
 * @Date 2018-09-01 22:22:16
 **/
@Controller
@RequestMapping("/blog")
public class BlogController {
    private static final Logger logger = LoggerFactory.getLogger(BlogController.class);


    @Autowired
    private BlogService blogService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private FollowService followService;

    @Autowired
    private EventProducer eventProducer;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String addBlog(String title, String content) {
        try {

            if (StringUtils.isBlank(title) || StringUtils.isBlank(content)) {
                return CommonUtil.getJSONString(1, "添加失败,请输入博客内容");
            }
            Blog blog = new Blog();
            blog.setContent(content.replace("\"", "\'"));
            blog.setTitle(HtmlUtils.htmlEscape(title));
            blog.setUserId(hostHolder.getUser().getId());
            blog.setCommentCount(0);
            blog.setCreatedDate(LocalDateTime.now());

            if (blogService.addBlog(blog) > 0) {
                eventProducer.fireHandler(new EventModel().setActorId(hostHolder.getUser().getId())
                        .setEntityType(EntityType.ENTITY_BLOG)
                        .setEntityId(blog.getId())
                        .setEventType(EventType.ADD_BLOG)
                        .set("blogTitle", blog.getTitle())
                        .set("blogId", String.valueOf(blog.getId()))
                        .set("blogContent", CommonUtil.deleteAllHTMLTag(blog.getContent()))

                );
                return CommonUtil.getJSONString(0);
            }
            return CommonUtil.getJSONString(1, "添加失败");

        } catch (Exception e) {
            logger.error("添加博客失败", e.getMessage());
            return CommonUtil.getJSONString(1,"添加失败");
        }
    }


    @RequestMapping("/delete/{id}")
    public String deleteBlog(@PathVariable("id") int id) {

        try {


            Blog blogById = blogService.getBlogById(id);
            if (blogById == null || blogById.getUserId() != hostHolder.getUser().getId()) {

                return "redirect:/user/" + hostHolder.getUser().getId();
            }
            blogService.deleteBlog(id);
            eventProducer.fireHandler(new EventModel(EventType.DELETE_BLOG).setActorId(hostHolder.getUser().getId())
                    .setEntityType(EntityType.ENTITY_BLOG)
                    .setEntityId(blogById.getId()));
        } catch (Exception e) {
            logger.error("删除博客失败...", e.getMessage());
        }

        return "redirect:/user/" + hostHolder.getUser().getId();
    }


    @RequestMapping("/{id}")
    public String blogDetail(Model model, @PathVariable("id") int id) {

        try {

            Blog blog = blogService.getBlogById(id);

            List<Comment> commentList = commentService.getCommentsByEntity(id, EntityType.ENTITY_BLOG);

            List<ViewObject> comments = new ArrayList<>();

            for (Comment comment : commentList) {
                ViewObject vo = new ViewObject();
                long likeCount = likeService.getLikeCount(EntityType.ENTITY_COMMENT, comment.getId());
                long likeStatus = likeService.getLikeStatus(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, comment.getId());
                vo.set("comment", comment);
                vo.set("user", userService.getUserById(comment.getUserId()));
                vo.set("likeCount", likeCount);
                vo.set("liked", likeStatus);
                comments.add(vo);
            }

            List<Integer> followersId = followService.getFollowersId(EntityType.ENTITY_BLOG, id, 0, 10);
            List<User> followUsers = new ArrayList<>();
            followersId.forEach(followUserId -> {
                followUsers.add(userService.getUserById(followUserId));
            });
            model.addAttribute("blog", blog);
            model.addAttribute("comments", comments);
            model.addAttribute("followUsers", followUsers);
            model.addAttribute("followed", followService.isFollower(hostHolder.getUser().getId(), EntityType.ENTITY_BLOG, id) ? 666 : null);
        } catch (Exception e) {
            logger.error("查询博客详情失败...", e.getMessage());
        }
        return "detail";
    }


}
