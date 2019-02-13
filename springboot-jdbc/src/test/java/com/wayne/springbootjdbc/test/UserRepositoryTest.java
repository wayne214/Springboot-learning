package com.wayne.springbootjdbc.test;

import com.wayne.springbootjdbc.model.User;
import com.wayne.springbootjdbc.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JdbcTemplate primaryJdbcTemplate;
    @Autowired
    private JdbcTemplate secondaryJdbcTemplate;
    @Test
    public void testSave() {
        User user = new User("wayne", "1234", 20);
        userRepository.save(user, primaryJdbcTemplate);
        userRepository.save(user, secondaryJdbcTemplate);
    }

    /**
     * 测试更新
     */
    @Test
    public void testUpdate() {
        User user = new User("neo","123456", 18);
        user.setId(1L);
//        userRepository.update(user, primaryJdbcTemplate);
        userRepository.update(user, secondaryJdbcTemplate);
    }

    /**
     * 测试查询
     */
    @Test
    public void testQueryOne()  {
        User user=userRepository.findById(1L, primaryJdbcTemplate);
        System.out.println("user == "+user.toString());
    }

    /**
     * 测试查询所有用户
     */
    @Test
    public void testQueryAll() {
        List<User> userList = userRepository.findAll(secondaryJdbcTemplate);
        for(User user : userList) {
            System.out.println("users=="+user.toString());
        }
    }
}
