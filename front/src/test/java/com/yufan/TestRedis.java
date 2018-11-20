package com.yufan;

import com.yufan.redis.RedisService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
public class TestRedis {

    @Autowired
    private RedisService redisService;


    @Test
    public void test6(){
        String name = redisService.get("name");
        System.out.println(name+"---------------------------------------------------");
    }


    @Test
    public void test1(){

        //Jedis jedis=new Jedis("localhost");

        JedisPool jedisPool=new JedisPool("localhost",6379);

        Jedis jedis = jedisPool.getResource();
        //jedis.set("name","zhangfei");

        String name=jedis.get("name");

        System.out.println(name);
    }

    @Test
    public void test2(){

        Jedis jedis=new Jedis("localhost");
       // jedis.hset("map","001","aaa");
        //jedis.hset("map","002","bbb");

        String value1 = jedis.hget("map", "001");
        String value2 = jedis.hget("map", "002");

        System.out.println(value1);
        System.out.println(value2);

    }

    @Test
    public void test3(){

        Jedis jedis=new Jedis("localhost");

        jedis.lpush("aa","001");
        jedis.lpush("aa","002");
        jedis.lpush("aa","003");
        jedis.lpush("aa","004");
        jedis.lpush("aa","005");

        String aa = jedis.lpop("aa");

        System.out.println(aa);
    }

}
