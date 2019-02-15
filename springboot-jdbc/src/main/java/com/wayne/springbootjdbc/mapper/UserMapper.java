package com.wayne.springbootjdbc.mapper;

import com.wayne.springbootjdbc.model.User;
import com.wayne.springbootjdbc.param.UserParam;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserMapper {

    List<User> getAll();

    /**
     * type：动态生成 SQL 的类
     * method：类中具体的方法名
     * @param userParam
     * @return
     */
    @SelectProvider(type = UserSql.class, method = "getList")
    List<User> getList(UserParam userParam);

    User getOne(Long id);

    void insert(User user);

    @Update("UPDATE users SET userName=#{userName}, nick_name=#{nickName} WHERE id =#{id}")
    void update(User user);

    @Update({"<script> ",
            "update users " ,
            "<set>" ,
            " <if test='userName != null'>userName=#{userName},</if>" ,
            " <if test='nickName != null'>nick_name=#{nickName},</if>" ,
            " </set> ",
            "where id=#{id} " ,
            "</script>"})
    void updateUser(User user);

    void delete(Long id);
}
