package com.xhn.test.mapper;

import com.xhn.BaseTest;
import com.xhn.test.demo.AsyncDemo;
import org.junit.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author: 86188
 * @date: 2021/3/27
 * @desc
 */
public class TestUserMapperTest extends BaseTest {
    @Autowired
    TestUserMapper testUserMapper;

    @Autowired
    JedisCluster jedisCluster;

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    AsyncDemo asyncDemo;

    @Test
    public void testGetUserInfo() {
        // TestUser userInfo = testUserMapper.getUserInfo();
        // System.out.println(userInfo);
    }

    @Test
    public void testAsync() throws ExecutionException, InterruptedException {
       asyncDemo.asyncInvokeSimplest();
       asyncDemo.asyncInvokeWithParameter("MFL");
        Future<String> stringFuture = asyncDemo.asyncInvokeReturnFuture(12);
        System.out.println(stringFuture.get());
    }

    @Test
    public void testGet() {
        String lockKey = UUID.randomUUID().toString().replace("-", "") + Thread.currentThread().getName();
        System.out.println(lockKey);
        RLock lock = redissonClient.getLock(lockKey);
        taskExecutor.execute(() -> {
            System.out.println(Thread.currentThread().getName());
        });


        try {
            lock.lock(10, TimeUnit.SECONDS);
            System.out.println("sss");
        } catch (Exception ex) {
            System.out.println("获取锁超时");
        } finally {
            lock.unlock();
        }


    }


    @Test
    public void testCluster() {
        jedisCluster.set("1994", "born");
        System.out.println(jedisCluster.get("1994"));
    }

    @Test
    public void testOneRedis() {
        Jedis jedis = new Jedis("192.168.175.128", 6379);
        System.out.println(jedis.get("a"));
    }
}
