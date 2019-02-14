package com.wayne.springdatajpa.Test;

import com.wayne.springdatajpa.model.User;
import com.wayne.springdatajpa.repository.UserRepository;
import com.wayne.springdatajpa.repository.test1.UserTest1Repository;
import com.wayne.springdatajpa.repository.test2.UserTest2Repository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTests {

    @Resource
    private UserRepository userRepository;

    @Resource
    private UserTest1Repository userTest1Repository;

    @Resource
    private UserTest2Repository userTest2Repository;

    @Test
    public void test() {
        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
        String formatedDate = dateFormat.format(date);

        userRepository.save(new User("aa", "aa@126.com", "aa", "aa123456", formatedDate));
        userRepository.save(new User("bb", "bb@126.com", "bb", "bb123456", formatedDate));
        userRepository.save(new User("cc", "cc@126.com", "cc", "cc123456", formatedDate));

        Assert.assertEquals(9, userRepository.findAll().size());
        Assert.assertEquals("bb", userRepository.findByUserNameOrEmail("bb","cc@126.com").getNickName());
        userRepository.delete(userRepository.findByUserName("aa1"));
    }
    @Test
    public void testPageQuery() {
        int page=1, size = 2;
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);
        userRepository.findAll(pageable);
        userRepository.findByNickName("aa", pageable);
    }

    @Test
    public void testSave() throws Exception {
        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
        String formattedDate = dateFormat.format(date);

        userTest1Repository.save(new User("aa", "aa123456","aa@126.com", "aa",  formattedDate));
        userTest1Repository.save(new User("bb", "bb123456","bb@126.com", "bb",  formattedDate));
        userTest2Repository.save(new User("cc", "cc123456","cc@126.com", "cc",  formattedDate));
    }

    @Test
    public void testDelete() throws Exception {
        userTest1Repository.deleteAll();
        userTest2Repository.deleteAll();
    }
}
