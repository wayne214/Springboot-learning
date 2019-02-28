package com.wayne.radis;

import com.wayne.radis.model.User;
import jdk.management.resource.internal.inst.FileOutputStreamRMHooks;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;
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

    /**
     * Hash(哈希)测试
     * Hash set 的时候需要传入三个参数，
     * 第一个为 key，第二个为 Field，第三个为存储的值。
     * 一般情况下 Key 代表一组数据，Field 为 key 相关的属性，而 Value 就是属性对应的值。
     */
    @Test
    public void testHash() {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.put("hash", "you", "know");
        String value = (String) hash.get("hash", "you");
        System.out.println("hash value: "+value);
    }

    /**
     * List
     * Redis List 的实现为一个双向链表，即可以支持反向查找和遍历，更方便操作，
     * 不过带来了部分额外的内存开销，Redis 内部的很多实现，包括发送缓冲队列等也都是用的这个数据结构
     */
    @Test
    public void testList() {
        ListOperations<String, String> list = redisTemplate.opsForList();
        list.leftPush("list","it");
        list.leftPush("list", "you");
        list.leftPush("list", "know");
        String value = (String) list.leftPop("list");
        System.out.println("list value :"+value);
        // range 后面的两个参数就是插入数据的位置，输入不同的参数就可以取出队列中对应的数据。
        List<String> values=list.range("list",0,2);
        for (String v:values){
            System.out.println("list range :"+v);
        }
    }

    /**
     * Set
     * Redis Set 对外提供的功能与 List 类似是一个列表的功能，特殊之处在于 Set 是可以自动排重的，
     * 当你需要存储一个列表数据，又不希望出现重复数据时，Set 是一个很好的选择，
     * 并且 Set 提供了判断某个成员是否在一个 Set 集合内的重要接口，这个也是 List 所不能提供的
     *
     * Set 的内部实现是一个 Value 永远为 null 的 HashMap，
     * 实际就是通过计算 Hash 的方式来快速排重，
     * 这也是 Set 能提供判断一个成员是否在集合内的原因。
     */
    @Test
    public void testSet() {
        String key = "set";
        SetOperations<String, String> set = redisTemplate.opsForSet();
        set.add(key,"it");
        set.add(key,"you");
        set.add(key,"you");
        set.add(key,"know");
        Set<String> members = set.members(key);
        for (String value: members) {
            System.out.println("set value:" + value);
        }

        Boolean hah = set.isMember(key, "hah");
        if (hah) {
            System.out.println("hah is true");
        } else {
            System.out.println("hah is false");
        }

        // 测试difference
        // difference() 函数会把 key 1 中不同于 key 2 的数据对比出来，这个特性适合我们在金融场景中对账的时候使用
        SetOperations<String, String> difference = redisTemplate.opsForSet();
        String key1="setMore1";
        String key2="setMore2";
        difference.add(key1,"it");
        difference.add(key1,"you");
        difference.add(key1,"you");
        difference.add(key1,"know");
        difference.add(key2,"xx");
        difference.add(key2,"know");
        Set<String> diffs=difference.difference(key1,key2);
        for (String v:diffs){
            System.out.println("diffs set value :"+v);
        }

        // unions 会取两个集合的合集
        SetOperations<String, String> unionsSet = redisTemplate.opsForSet();
        String key3="setMore3";
        String key4="setMore4";
        unionsSet.add(key3,"it");
        unionsSet.add(key3,"you");
        unionsSet.add(key3,"xx");
        unionsSet.add(key4,"aa");
        unionsSet.add(key4,"bb");
        unionsSet.add(key4,"know");
        Set<String> unions=unionsSet.union(key3,key4);
        for (String v:unions){
            System.out.println("unions value :"+v);
        }

    }

    /**
     * ZSet
     * Redis Sorted Set 的使用场景与 Set 类似，区别是 Set 不是自动有序的，
     * 而 Sorted Set 可以通过用户额外提供一个优先级（Score）的参数来为成员排序，
     * 并且是插入有序，即自动排序。
     *
     * 在使用 Zset 的时候需要额外的输入一个参数 Score，
     * Zset 会自动根据 Score 的值对集合进行排序，我们可以利用这个特性来做具有权重的队列，
     * 比如普通消息的 Score 为1，重要消息的 Score 为 2，
     * 然后工作线程可以选择按 Score 的倒序来获取工作任务。
     *
     * Redis Sorted Set 的内部使用 HashMap 和跳跃表（SkipList）来保证数据的存储和有序，
     * HashMap 里放的是成员到 Score 的映射，而跳跃表里存放的是所有的成员，
     * 排序依据是 HashMap 里存的 Score，使用跳跃表的结构可以获得比较高的查找效率，并且在实现上比较简单。
     */
    @Test
    public void testZset() {
        String key = "zset";
        redisTemplate.delete(key);
        ZSetOperations<String, String> zSet = redisTemplate.opsForZSet();
        zSet.add(key, "it", 1);
        zSet.add(key,"you",6);
        zSet.add(key,"know",4);
        zSet.add(key,"neo",3);


        Set<String> zsets = zSet.range(key, 0, 3);
        for (String v: zsets){
            System.out.println("zset value: "+v);
        }
        // Redis 还提供了 rangeByScore 这样的一个方法，可以只获取 Score 范围内排序后的数据。
        Set<String> zsetB = zSet.rangeByScore(key, 0, 3);
        for (String v: zsetB) {
            System.out.println("zsetB value: "+ v);
        }
    }
}
