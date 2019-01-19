package cn.edu.ncu.ncublog.dao;

import cn.edu.ncu.ncublog.model.Feed;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author lw
 * @Date 2018-09-04 12:42:25
 **/

@Mapper
public interface FeedDao {

    String TABLE_NAME = " feed ";
    String INSERT_FIELDS = " user_id, data, created_date, type ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ")", " values (#{userId}, #{data},#{createdDate}, #{type})"})
    int addFeed(Feed feed);

    List<Feed> selectLatestFeedsByUserIds(@Param("userIds") List<Integer> userIds, @Param("offset") int offset, @Param("limit") int limit);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    Feed selectFeedById(int id);



}
