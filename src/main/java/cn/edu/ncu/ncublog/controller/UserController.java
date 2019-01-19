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
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Author lw
 * @Date 2018-09-01 17:52:07
 **/
@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);



    @Autowired
    private FollowService followService;

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private CommentService commentService;


    @Autowired
    private BlogService blogService;

    public static final String DEFAULT_UPLOAD_ADDRESS = "\\static\\images\\headers\\";
    public static final String DEFAULT_HEADER_ADDRESS = "/images/headers/";



    @RequestMapping("/{id}")
    public String profile(Model model, @PathVariable("id") int id) {

        boolean followed = followService.isFollower(hostHolder.getUser().getId(), EntityType.ENTITY_USER, id);
        long followerCount = followService.getFollowerCount(EntityType.ENTITY_USER, id);
        long followeeCount = followService.getFolloweeCount(id, EntityType.ENTITY_USER);
        int commentCountByUserId = commentService.getCommentCountByUserId(id);
        User user = userService.getUserById(id);
        List<Blog> blogsByUserId = blogService.getBlogsByUserId(id);
        List<ViewObject> vos = new ArrayList<>();

        blogsByUserId.forEach(blog -> {
            ViewObject viewObject = new ViewObject();
            String s = CommonUtil.deleteAllHTMLTag(blog.getContent());
            s = StringUtils.isBlank(s) ? "[图片]" : s;
            blog.setContent(s);
            viewObject.set("blog", blog);
            viewObject.set("followCount", followService.getFollowerCount(EntityType.ENTITY_BLOG, blog.getId()));
            viewObject.set("user", userService.getUserById(blog.getUserId()));
            blog.setCommentCount(commentService.getCommentCount(blog.getId(), EntityType.ENTITY_BLOG));

            vos.add(viewObject);
        });
        ViewObject profileUser = new ViewObject();

        profileUser.set("user", user);

        profileUser.set("followed", followed ? 666 : null);
        profileUser.set("followerCount", followerCount);
        profileUser.set("followeeCount", followeeCount);
        profileUser.set("commentCount", commentCountByUserId);
        model.addAttribute("profileUser", profileUser);
        model.addAttribute("vos", vos);
        return "profile";
    }

    @RequestMapping("/edit")
    public String edit() {

        return "updateinfo";
    }

    @RequestMapping("/updateInfo")
    public String updateinf(@RequestParam("inputfile") MultipartFile picFile, User user, HttpServletRequest request) throws FileNotFoundException {
        User currUser = hostHolder.getUser();
        //System.out.println(user);
        try {

            user.setId(currUser.getId());

            if (StringUtils.isBlank(user.getName()) || user.getName().equals(currUser.getName())) {
                user.setName(null);
            }

            if (StringUtils.isNoneBlank(user.getPassword())) {
                String salt = UUID.randomUUID().toString().substring(0, 5);
                user.setSalt(salt);
                user.setPassword(CommonUtil.MD5(user.getPassword() + salt));
            } else {
                user.setPassword(null);
            }

            if (StringUtils.isBlank(user.getVocation())) {
                user.setVocation(null);
            }

            if (StringUtils.isBlank(user.getIntroduction())) {
                user.setIntroduction(null);

            }
        //System.out.println(user);
            if (!picFile.isEmpty()) {


                // 图片新名字
                String name = UUID.randomUUID().toString();
                // 图片原名字
                String oldName = picFile.getOriginalFilename();
                // 后缀名
                String exeName = oldName.substring(oldName.lastIndexOf("."));

                if (exeName == null) {
                    exeName = "jpg";
                }
                File path = new File(ResourceUtils.getURL("classpath:").getPath());
                if(!path.exists()){
                    path=new File("");
                }
                String filePath = path + DEFAULT_UPLOAD_ADDRESS;

                //如果上传目录为/static/images/upload/,则可以如下获取
                //File filePath=new File(path.getAbsolutePath(),DEFAULT_UPLOAD_ADDRESS);

                String fileName = name + exeName;
                try {
                    picFile.transferTo(new File(filePath + fileName));
                    user.setHeadUrl(DEFAULT_HEADER_ADDRESS + fileName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            userService.changeUserInfo(user);
        } catch (Exception e) {

            logger.error("修改信息失败...", e.getMessage());
        }

        return "redirect:/user/" + currUser.getId();
    }

    @RequestMapping(value = "/{id}/followers")
    public String followers(Model model, @PathVariable("id") int id, @RequestParam(value = "offset", required = false, defaultValue = "0") int offset, @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {


        try {


            User user = userService.getUserById(id);
            List<Integer> followersId = followService.getFollowersId(EntityType.ENTITY_USER, id, offset, limit);
            long followerCount = followService.getFollowerCount(EntityType.ENTITY_USER, id);
            List<User> users = getUsersByIds(followersId);

            List<ViewObject> followers = new ArrayList<>();
            users.forEach(followerUser -> {
                ViewObject viewObject = new ViewObject();
                viewObject.set("user", followerUser);
                viewObject.set("followed", followService.isFollower(hostHolder.getUser().getId(), EntityType.ENTITY_USER, followerUser.getId()) ? 666 : null);
                viewObject.set("commentCount", commentService.getCommentCountByUserId(followerUser.getId()));
                //viewObject.set("");
                long followerCount1 = followService.getFollowerCount(EntityType.ENTITY_USER, followerUser.getId());
                long followeeCount = followService.getFolloweeCount(followerUser.getId(), EntityType.ENTITY_USER);
                viewObject.set("followerCount", followerCount1);
                viewObject.set("followeeCount", followeeCount);

                followers.add(viewObject);
            });

            model.addAttribute("followers", followers);
            model.addAttribute("curUser", user);
            model.addAttribute("followerCount", followerCount);
        } catch (Exception e) {
            logger.error("查询粉丝失败...", e.getMessage());
        }
        return "followers";

    }

    @RequestMapping(value = "/{id}/followees")
    public String followees(Model model, @PathVariable("id") int id, @RequestParam(value = "offset", required = false, defaultValue = "0") int offset, @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {

        try {


            User user = userService.getUserById(id);
            List<Integer> followeesId = followService.getFolloweesId(EntityType.ENTITY_USER, id, offset, limit);
            long followeeCount = followService.getFolloweeCount(id, EntityType.ENTITY_USER);
            List<User> users = getUsersByIds(followeesId);

            List<ViewObject> followees = new ArrayList<>();
            users.forEach(followerUser -> {
                ViewObject viewObject = new ViewObject();
                long followerCount1 = followService.getFollowerCount(EntityType.ENTITY_USER, followerUser.getId());
                long followeeCount1 = followService.getFolloweeCount(followerUser.getId(), EntityType.ENTITY_USER);
                viewObject.set("user", followerUser);
                viewObject.set("followed", followService.isFollower(hostHolder.getUser().getId(), EntityType.ENTITY_USER, followerUser.getId()) ? 666 : null);
                viewObject.set("commentCount", commentService.getCommentCountByUserId(followerUser.getId()));
                viewObject.set("followerCount", followerCount1);
                viewObject.set("followeeCount", followeeCount1);

                followees.add(viewObject);
            });

            model.addAttribute("followees", followees);
            model.addAttribute("curUser", user);
            model.addAttribute("followeeCount", followeeCount);
        } catch (Exception e) {
            logger.error("查询关注人失败...", e.getMessage());
        }
        return "followees";

    }

    private List<User> getUsersByIds(List<Integer> ids) {
        List<User> users = new ArrayList<>();
        ids.forEach(id -> {
            users.add(userService.getUserById(id));
        });
        return users;
    }

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        //System.out.println("fffff");
        return "Thinking in Java";
    }

    @RequestMapping("/test1")
    public String test1() {
        //System.out.println("UserController.test1");
        return "index";
    }


}
