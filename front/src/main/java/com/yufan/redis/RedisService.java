package com.yufan.redis;

public interface RedisService {

    public String get(String key);//获取string的值
    public String set(String key, String value);//设置string的值
    public String hget(String hkey, String key);//获取hash的值
    public long hset(String hkey, String key, String value);//设置hansh的值
    public long incr(String key);//递增
    public long decr(String key);//递减
    public long ttl(String key);//查看剩余时间
    public long del(String key);//删除指定的键
    public long hdel(String hkey, String key);//删除hash指定的key
    public long expire(String key, int time);//设置过期时间

}
