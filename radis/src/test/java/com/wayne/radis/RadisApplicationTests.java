package com.wayne.radis;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

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

}
