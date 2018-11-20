package com.yufan.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.yufan.bean.Item;
import com.yufan.exception.CustomerException;
import com.yufan.redis.RedisService;
import com.yufan.rpc.service.ItemRpcService;
import com.yufan.service.ItemService;
import com.yufan.utils.JsonUtils;

@Service
public class ItemServiceImpl implements ItemService {

    private Logger LOGGER=LoggerFactory.getLogger(ItemServiceImpl.class);

    private static final String ITEM_CACHE="item:";

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
            return JsonUtils.jsonToPojo(json,Item.class);
        }

        //2.缓存没有
        LOGGER.debug("缓存中没有找到数据，调用后台系统,商品id为:{}",itemId);
        Item item = itemRpcService.queryItemById(itemId);
        if(item==null){
            LOGGER.error("后台系统商品查询失败!商品id为{}:",itemId);
            throw  new CustomerException("后台系统商品查询失败!请输入正确的id");
        }
        json=JsonUtils.objectToJson(item);

        redisService.set(ITEM_CACHE+itemId,json);
        //设置过期时间
        redisService.expire(ITEM_CACHE+itemId,60*30);
        return item;
    }

    @Override
    public void deleteCache(Long itemId) {
        LOGGER.debug("删除缓存,商品id为{}",itemId);
        redisService.del(ITEM_CACHE+itemId);
    }

}
