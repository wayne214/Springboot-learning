package com.wayne.radis;

import com.wayne.radis.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RadisApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    public void contextLoads() {
    }
    @Test
    public void testString() {
        redisTemplate.opsForValue().set("neo","ityouknow");
        Assert.assertEquals("ityouknow", redisTemplate.opsForValue().get("neo"));
    }

    /**
     * 测试对Object的支持
     */
    @Test
    public void testObj() {
        User user = new User("wayne214","123456","10329@qq.com","wayne","2019");
        ValueOperations<String, User> operations = redisTemplate.opsForValue();
        operations.set("com.wayne", user);
        User u = operations.get("com.wayne");
        System.out.println("user:"+u.toString());
    }

    /**
     * 测试数据超时失效
     * @throws InterruptedException
     */
    @Test
    public void testExpire() throws InterruptedException {
        User user = new User("expire","123456","10329@qq.com","expire","2019");
        ValueOperations<String, User> operations = redisTemplate.opsForValue();
        operations.set("expire", user, 100, TimeUnit.MILLISECONDS);
        Thread.sleep(1000);
        Boolean exist = redisTemplate.hasKey("expire");
        if (exist) {
            System.out.println("exist is true");
        } else {
            System.out.println("exist is false");
        }
    }

    /**
     * 删除数据
     */
    @Test
    public void testDelete() {
        ValueOperations<String, User> operations = redisTemplate.opsForValue();
        redisTemplate.opsForValue().set("deletekey","wayne214");
        redisTemplate.delete("deletekey");
        Boolean exists = redisTemplate.hasKey("deletekey");
        if (exists) {
            System.out.println("exist is true");
        } else {
            System.out.println("exist is false");
        }
    }
}
