package cn.edu.ncu.ncublog.dao;

import cn.edu.ncu.ncublog.model.Message;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author lw
 * @Date 2018-09-02 20:34:41
 **/
@Mapper
public interface MessageDao {
    String TABLE_NAME = " message ";
    String INSERT_FIELDS = " from_id, to_id, content, has_read, conversation_id, created_date ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;
    String SELECT_FILEDS_CONVERSATION = " tmp.id AS id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ")", "values(#{fromId},#{toId},#{content},#{hasRead},#{conversationId},#{createdDate})"})
    int addMessage(Message message);

    /**
     *   SELECT id, from_id, to_id, content, created_date, has_read, conversation_id
     *   FROM
     * 	message,
     * 	(select conversation_id as conver_id,max(created_date) as max_date from message  group by conversation_id order by max_date desc)
     *   AS 	tmp
     *   where conversation_id=tmp.conver_id and created_date=tmp.max_date limit 0,1;
     * @param userId
     * @param offset
     * @param limit
     * @return
     */
    @Select({"select ", SELECT_FILEDS_CONVERSATION, " from message, ",
            "(select conversation_id as conver_id,max(created_date) as max_date, count(id) as id from message  group by conversation_id order by max_date desc)",
            " as tmp  where conversation_id=tmp.conver_id and created_date=tmp.max_date ",
            " and (from_id=#{userId} or to_id=#{userId}) order by created_date desc limit #{offset},#{limit};"
    })
    List<Message> selectConversationList(@Param("userId") int userId, @Param("offset") int offset, @Param("limit") int limit);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where conversation_id=#{conversationId} order by created_date desc limit #{offset},#{limit}"})
    List<Message> selectCoversationDetail(@Param("conversationId") String conversationId, @Param("offset") int offset, @Param("limit") int limit);

    @Select({"select count(id) from ", TABLE_NAME, " where has_read=0 and to_id=#{userId} and conversation_id=#{conversationId}"})
    int selectConversationUnreadCount(@Param("userId") int userId, @Param("conversationId") String conversationId);

    @Update({"update ", TABLE_NAME, " set has_read=1 where to_id=#{userId} and conversation_id=#{conversationId}"})
    int updateReadStatus(@Param("userId") int userId, @Param("conversationId") String converstaionId);
}
