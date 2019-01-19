package cn.edu.ncu.ncublog;

import cn.edu.ncu.ncublog.dao.CommentDao;
import cn.edu.ncu.ncublog.model.Blog;
import cn.edu.ncu.ncublog.model.Comment;
import cn.edu.ncu.ncublog.model.EntityType;
import cn.edu.ncu.ncublog.model.Status;
import cn.edu.ncu.ncublog.service.BlogService;
import cn.edu.ncu.ncublog.service.CommentService;
import cn.edu.ncu.ncublog.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NcublogApplicationTests {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    BlogService blogService;

    @Autowired
    CommentService commentService;

    @Autowired
    CommentDao commentDao;

    @Test
    public void test() {
        List<Blog> latestBlogs = blogService.getLatestBlogs(0, 0, 10);
        System.out.println(latestBlogs.size());
    }

    @Test
    public void contextLoads() {
       /* redisUtil.set("java", "javac");
        System.out.println(redisUtil.get("java"));*/
    }

    @Test
    public void test2() {
        Comment comment = new Comment();
        comment.setContent("666");
        comment.setCreatedDate(LocalDateTime.now());
        comment.setEntityId(2);
        comment.setEntityType(EntityType.ENTITY_BLOG);
        comment.setUserId(3);

        commentService.addComment(comment);

    }

    @Test
    public void test3() {
        //System.out.println(commentService.deleteComment(1));
        System.out.println(commentDao.updateStatus(2, Status.INVALID));
    }


    //RedisTemplate<O>

    @Autowired
    public StringRedisTemplate stringRedisTemplate;

    @Test
    public void test4() {
        stringRedisTemplate.opsForValue().set("javacc", "javacc");
        System.out.println(stringRedisTemplate.opsForValue().get("javacc"));

    }

    @Test
    public void test5() {
        redisUtil.srem("java", "ksdjflsdkfjsdlfj");
        System.out.println("done...");
    }

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void test6() {
        System.out.println(applicationContext);
    }

}
