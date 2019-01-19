package cn.edu.ncu.ncublog.service;

import cn.edu.ncu.ncublog.dao.BlogDao;
import cn.edu.ncu.ncublog.model.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author lw
 * @Date 2018-09-01 12:18:57
 **/
@Service
@Transactional
public class BlogService {

    @Autowired
    private BlogDao blogDao;

    public List<Blog> getLatestBlogs(int userId, int offset, int limit) {
        return blogDao.selectLatestBlogs(userId, offset, limit);
    }

    public int addBlog(Blog blog) {
        return blogDao.addBlog(blog) > 0 ? blog.getId() : 0;
    }

    public Blog getBlogById(int id) {
        return blogDao.selectBlogById(id);
    }

    public int getUserIdById(int id) {
        return blogDao.selectUserIdById(id);
    }

    public List<Blog> getBlogsByUserId(int userId) {
        return blogDao.selectBlogsByUserId(userId);
    }


    public int deleteBlog(int id) {
        return blogDao.updateBlogStatus(id);
    }

    public long getBlogCount() {

        return blogDao.selectBlogCount();
    }

}
