package com.yufan.redis;

import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisServiceImpl implements RedisService{

    @Autowired
    private JedisPool jedisPool;

    public Jedis getJedis(){
        return jedisPool.getResource();
    }

    @Override
    public String get(String key) {
        Jedis jedis = getJedis();
        String result = jedis.get(key);
        jedis.close();//还给连接池
        return result;
    }

    @Override
    public String set(String key, String value) {
        Jedis jedis = getJedis();
        String result=jedis.set(key,value);
        jedis.close();
        return result;
    }

    @Override
    public String hget(String hkey, String key) {
        Jedis jedis = getJedis();
        String result=jedis.hget(hkey,key);
        jedis.close();
        return result;
    }

    @Override
    public long hset(String hkey, String key, String value) {
        Jedis jedis = getJedis();
        Long result = jedis.hset(hkey, key, value);
        jedis.close();
        return result;
    }

    @Override
    public long incr(String key) {
        Jedis jedis = getJedis();
        Long incr = jedis.incr(key);
        jedis.close();
        return incr;
    }

    @Override
    public long decr(String key) {
        Jedis jedis = getJedis();
        Long result = jedis.decr(key);
        jedis.close();
        return result;
    }

    @Override
    public long ttl(String key) {
        Jedis jedis = getJedis();
        Long result = jedis.ttl(key);
        jedis.close();
        return result;
    }

    @Override
    public long del(String key) {
        Jedis jedis = getJedis();
        Long result = jedis.del(key);
        jedis.close();
        return result;
    }

    @Override
    public long hdel(String hkey, String key) {
        Jedis jedis = getJedis();
        Long result = jedis.hdel(hkey,key);
        jedis.close();
        return result;
    }

    @Override
    public long expire(String key, int time) {
        Jedis jedis = getJedis();
        Long result = jedis.expire(key,time);
        jedis.close();
        return result;
    }

    @Override
    public Boolean exists(String keyName) {
        Jedis jedis = getJedis();
        Boolean result = jedis.exists(keyName);
        jedis.close();
        return result;
    }
}
