package cn.edu.ncu.ncublog.dao;

import cn.edu.ncu.ncublog.model.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author lw
 * @Date 2018-09-02 16:09:37
 **/

/*
CREATE TABLE `comment` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `content` TEXT NOT NULL,
  `user_id` INT NOT NULL,
  `entity_id` INT NOT NULL,
  `entity_type` INT NOT NULL,
  `created_date` DATETIME NOT NULL,
  `status` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX `entity_index` (`entity_id` ASC, `entity_type` ASC)
  ) ENGINE=INNODB DEFAULT CHARSET=utf8;
 */
/*

    private int id;
    private String content;
    private int userId;
    private int EntityId;
    private int EntityType;
    private LocalDateTime createDate;
    private int status;
 */
@Mapper
public interface CommentDao {

    String TABLE_NAME = " comment ";
    String INSERT_FIELDS = " user_id, content, entity_id, entity_type, created_date, status ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;
    String SELECT = "select ";
    String FROM = " from ";
    String OPEN = " ( ";
    String CLOSE = " ) ";

    @Insert({"insert into ", TABLE_NAME, OPEN, INSERT_FIELDS, CLOSE, "values (#{userId},#{content},#{entityId},#{entityType},#{createdDate},#{status})"})
    int addComment(Comment comment);

    @Select({SELECT, SELECT_FIELDS, FROM, TABLE_NAME, " where id=#{id} and status=0"})
    Comment selectCommentById(int id);

    @Select({SELECT, " count(id) ", FROM, TABLE_NAME, " where status=0 and entity_id=#{entityId} and entity_type=#{entityType}"})
    int selectcommentCountByEntity(@Param("entityId") int entityId, @Param("entityType") int entityType);

    @Select({SELECT, " count(id) ", FROM, TABLE_NAME, " where status=0 and user_id=#{userId}"})
    int selectcommentCountByUserId(@Param("userId") int userId);


    @Select({SELECT, SELECT_FIELDS, FROM, TABLE_NAME, " where status=0 and entity_id=#{entityId} and entity_type=#{entityType}  order by created_date desc"})
    List<Comment> selectCommentsByEntity(@Param("entityId") int entityId, @Param("entityType") int entityType);

    @Update({"update ", TABLE_NAME, " set status=#{status} where id=#{id}"})
    int updateStatus(@Param("id") int id, @Param("status") int status);


}
