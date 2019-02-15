package com.wayne.springbootjdbc.repository.impl;

import com.wayne.springbootjdbc.model.User;
import com.wayne.springbootjdbc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    /**
     * JdbcTemplate是Spring操作JDBC的工具类
     */
    @Autowired
    private JdbcTemplate primaryJdbcTemplate;

    @Override
    public int save(User user, JdbcTemplate jdbcTemplate) {
        if (jdbcTemplate == null) {
            jdbcTemplate = primaryJdbcTemplate;
        }
        return jdbcTemplate.update("INSERT INTO users(name, password, age) values(?, ?, ?)", user.getUserName(), user.getPassword(), user.getAge());
    }

    @Override
    public int update(User user, JdbcTemplate jdbcTemplate) {
        if (jdbcTemplate == null) {
            jdbcTemplate = primaryJdbcTemplate;
        }
        return jdbcTemplate.update("UPDATE users SET name = ? , password = ? , age = ? WHERE id=?",
                user.getUserName(), user.getPassword(), user.getAge(), user.getId());
    }

    @Override
    public int delete(long id, JdbcTemplate jdbcTemplate) {
        if (jdbcTemplate == null) {
            jdbcTemplate = primaryJdbcTemplate;
        }
        return jdbcTemplate.update("DELETE FROM users where id = ? ",id);
    }

    @Override
    public List<User> findAll(JdbcTemplate jdbcTemplate) {
        if (jdbcTemplate == null) {
            jdbcTemplate = primaryJdbcTemplate;
        }
        return jdbcTemplate.query("SELECT * FROM users", new UserRowMapper());
    }

    @Override
    public User findById(long id, JdbcTemplate jdbcTemplate) {
        if (jdbcTemplate == null) {
            jdbcTemplate = primaryJdbcTemplate;
        }
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE id=?", new Object[] { id }, new BeanPropertyRowMapper<User>(User.class));
    }

    /**
     * UserRowMapper 继承了 RowMapper，RowMapper 可以将数据中的每一行数据封装成用户定义的类，
     * 实现 RowMapper 接口覆盖 mapRow 方法，在 mapRow 方法封装对数据的返回处理。
     * 通过上面代码可以看出 UserRowMapper 循环遍历了查询返回的结果集，遍历的同时按照属性进行赋值。
     * 这样在查询使用时只需要传入 new UserRowMapper() 即可自动解析返回数据。
     */
    class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setUserName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
            user.setAge(rs.getInt("age"));

            return user;
        }
    }
}
