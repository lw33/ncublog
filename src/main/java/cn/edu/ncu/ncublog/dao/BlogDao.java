package cn.edu.ncu.ncublog.dao;

import cn.edu.ncu.ncublog.model.Blog;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author lw
 * @Date 2018-09-01 11:40:41
 **/

@Mapper
public interface BlogDao {
    String TABLE_NAME = "blog";
    String INSERT_FIELDS = " title, content, created_date, user_id, comment_count ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ")", " values(#{title},#{content},#{createdDate},#{userId},#{commentCount})"})
    int addBlog(Blog blog);

    List<Blog> selectLatestBlogs(@Param("userId") int userId, @Param("offset") int offset, @Param("limit") int limit);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where user_id=#{userId} and status=0"})
    List<Blog> selectBlogsByUserId(@Param("userId") int userId);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id} and status=0"})
    Blog selectBlogById(int id);

    @Select({"select user_id",  " from ", TABLE_NAME, " where id=#{id} and status=0"})
    int selectUserIdById(int id);

    @Update({"update ", TABLE_NAME, " set status=1 where id=#{id}"})
    int updateBlogStatus(int id);

    @Select({"select count(id) from ", TABLE_NAME, " where status=0"})
    long selectBlogCount();



}
