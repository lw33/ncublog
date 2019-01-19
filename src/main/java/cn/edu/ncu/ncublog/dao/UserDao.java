package cn.edu.ncu.ncublog.dao;

import cn.edu.ncu.ncublog.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Author lw
 * @Date 2018-09-01 11:08:52
 **/

@Mapper
public interface UserDao {

    String TABLE_NAME = "user";
    String INSERT_FIELDS = " name, password, salt,head_url ";
    String INSERT_FIELDS_WITH_INFO = INSERT_FIELDS + " gender, birth, vocation, introduction ";
    String SELECT_FIELDS = " id, name, password, salt, gender, head_url ";
    String SELECT_FIELDS_WITH_INFO = SELECT_FIELDS + ", birth, vocation, introduction, register_date ";

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ", birth,register_date) values (#{name}, #{password}, #{salt}, #{headUrl}, #{birth},#{registerDate})"})
    int addUser(User user);

    @Select({"select ", SELECT_FIELDS_WITH_INFO, " from ", TABLE_NAME, " where id=#{id}"})
    User selectById(int id);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where name=#{name}"})
    User selectByName(String name);

    int updateUser(User user);


}
