package cn.edu.ncu.ncublog.controller;

import cn.edu.ncu.ncublog.model.*;
import cn.edu.ncu.ncublog.service.BlogService;
import cn.edu.ncu.ncublog.service.CommentService;
import cn.edu.ncu.ncublog.service.FollowService;
import cn.edu.ncu.ncublog.service.UserService;
import cn.edu.ncu.ncublog.util.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author lw
 * @Date 2018-09-01 07:53:35
 **/
@Controller
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);


    @Autowired
    private BlogService blogService;

    @Autowired
    private UserService userService;

    @Autowired
    private FollowService followService;

    @Autowired
    private CommentService commentService;

    @RequestMapping(path = {"/", "/index", "/index/{pageNum}"})
    public String index(Model model,
                        @RequestParam(value = "pop", defaultValue = "0") int pop, @PathVariable(value = "pageNum", required = false) Integer pageNum) {

        try {


            ViewObject pageInfo = new ViewObject();
            if (pageNum == null) {
                pageNum = 1;
            }
            List<Blog> blogs = blogService.getLatestBlogs(0, (pageNum - 1) * Config.PAGESIZE, Config.PAGESIZE);
            long blogCount = blogService.getBlogCount();
            pageInfo.set("pageNum", pageNum);
            pageInfo.set("pages", Math.ceil(Double.valueOf(blogCount) / Config.PAGESIZE));

            List<ViewObject> vos = new ArrayList<>();

            for (Blog blog : blogs) {
                ViewObject viewObject = new ViewObject();
                String s = CommonUtil.deleteAllHTMLTag(blog.getContent());
                s = StringUtils.isBlank(s) ? "[图片]" : s;
                blog.setContent(s);
                viewObject.set("blog", blog);
                viewObject.set("user", userService.getUserById(blog.getUserId()));
                viewObject.set("followCount", followService.getFollowerCount(EntityType.ENTITY_BLOG, blog.getId()));
                blog.setCommentCount(commentService.getCommentCount(blog.getId(), EntityType.ENTITY_BLOG));

                vos.add(viewObject);
            }
            model.addAttribute("pageInfo", pageInfo);
            model.addAttribute("vos", vos);
        } catch (Exception e) {
            logger.error("显示首页失败...", e.getMessage());
        }

        return "index";
    }
    @RequestMapping("/indextest")
    @ResponseBody
    public String test(Model model) {

        User user = new User();
        user.setName("Java");
        model.addAttribute("user", user);
        model.addAttribute("list", null);

        model.addAttribute("id", 1000);
        return "index";

    }


}
