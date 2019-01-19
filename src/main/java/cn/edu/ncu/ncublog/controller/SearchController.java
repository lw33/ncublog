package cn.edu.ncu.ncublog.controller;

import cn.edu.ncu.ncublog.model.Blog;
import cn.edu.ncu.ncublog.model.EntityType;
import cn.edu.ncu.ncublog.model.ViewObject;
import cn.edu.ncu.ncublog.service.*;
import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author lw
 * @Date 2018-09-04 22:46:28
 **/

@Controller
public class SearchController {

    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);



    @Autowired
    private SearchService searchService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private UserService userService;

    @Autowired
    private FollowService followService;

    @Autowired
    private CommentService commentService;

    @RequestMapping("/search")
    public String search(Model model, @RequestParam("q") String keyword,
                         @RequestParam(value = "offset", defaultValue = "0") int offset,
                         @RequestParam(value = "count", defaultValue = "10") int count) throws Exception {

        try {

            List<Blog> blogs = searchService.getBlogsByKeyword(keyword, offset, count, "<em>", "</em>");
            List<ViewObject> vos = new ArrayList<>();

            blogs.forEach(blog -> {

                ViewObject viewObject = new ViewObject();
                Blog blogById = blogService.getBlogById(blog.getId());
                if (blog.getContent() != null) {
                    blogById.setContent(blog.getContent());
                }
                if (blog.getTitle() != null) {
                    blogById.setTitle(blog.getTitle());
                }
                blogById.setCommentCount(commentService.getCommentCount(EntityType.ENTITY_BLOG, blogById.getId()));
                viewObject.set("blog", blogById);
                viewObject.set("user", userService.getUserById(blogById.getUserId()));
                viewObject.set("followCount", followService.getFollowerCount(EntityType.ENTITY_BLOG, blog.getId()));

                vos.add(viewObject);
            });


            model.addAttribute("vos", vos);
            model.addAttribute("keyword", keyword);
        } catch (Exception e) {
            logger.error("查询失败", e.getMessage());
        }


        return "sr";
    }



    @RequestMapping("/solr/test")
    @ResponseBody
    public String solrTest(String id) throws IOException, SolrServerException {
        return searchService.getBlogById(id);

    }
}
