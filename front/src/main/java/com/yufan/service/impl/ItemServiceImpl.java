package com.yufan.service.impl;

import com.yufan.service.ItemRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.yufan.bean.Item;
import com.yufan.exception.CustomerException;
import com.yufan.redis.RedisService;
import com.yufan.service.ItemService;
import com.yufan.utils.JsonUtils;

import java.util.Random;

@Service
public class ItemServiceImpl implements ItemService {

    private Logger LOGGER=LoggerFactory.getLogger(ItemServiceImpl.class);

    private static final String ITEM_CACHE="item:";
    private static final int ITEM_CACHE_EXPIRE=60*60*12;

    @Autowired
    private ItemRpcService itemRpcService;

    @Autowired
    private RedisService redisService;

    @Override
    public Item queryItemById(Long itemId) throws CustomerException {
        LOGGER.debug("查询的商品id为:{}",itemId);

        //1.先从redis中查找，如果没有再去调用后台系统
        String json = redisService.get(ITEM_CACHE + itemId);
        if(!StringUtils.isEmpty(json)){
            LOGGER.debug("从缓存中拿到商品的数据,商品id为:{}",itemId);
            if(json.equals("null")){
                throw new CustomerException("对不起，没有找到对应的商品！");
            }
            return JsonUtils.jsonToPojo(json,Item.class);
        }

        //2.缓存没有
        LOGGER.debug("缓存中没有找到数据，调用后台系统,商品id为:{}",itemId);
        Item item = itemRpcService.queryItemById(itemId);
        if(item==null){
            //防止缓存穿透，减少访问数据库的压力，给商品ID一个标示。
            redisService.set(ITEM_CACHE+itemId,"null");
            //设置缓存穿透的商品标示过期时间为5分钟
            redisService.expire(ITEM_CACHE+itemId,60*5);
            LOGGER.error("后台系统商品查询失败!商品id为{}:",itemId);
            throw  new CustomerException("对不起，没有找到对应的商品！");
        }
        json=JsonUtils.objectToJson(item);


        redisService.set(ITEM_CACHE+itemId,json);

        //设置随机过期时间，避免雪崩效应。
        int expireItme = new Random().nextInt(60*60*12);
        redisService.expire(ITEM_CACHE+itemId,ITEM_CACHE_EXPIRE+expireItme);
        LOGGER.info("生成缓存，缓存id为：{}缓存失效时间为：{}",ITEM_CACHE+itemId,ITEM_CACHE_EXPIRE+expireItme);
        return item;

    }

    @Override
    public void deleteCache(Long itemId) {
        LOGGER.debug("删除缓存,商品id为{}",itemId);
        redisService.del(ITEM_CACHE+itemId);
    }

}
