package com.yufan.service.impl;

import com.yufan.bean.Item;
import com.yufan.bean.ItemDesc;
import com.yufan.result.HttpClientUtil;
import com.yufan.result.Result;
import com.yufan.service.ItemDescService;
import com.yufan.service.ItemService;
import com.yufan.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

@Service
public class ItemServiceImpl extends BaseServiceImpl<Item> implements ItemService {

    private Logger LOGGER= LoggerFactory.getLogger(ItemServiceImpl.class);


    @Autowired
    private ItemDescService itemDescService;

    @Override
    public void itemAdd(Item item, String desc) {

        item.setStatus(Item.STATUS_DOWN);
        item.setUpdated(new Date());
        item.setCreated(item.getUpdated());
        this.save(item);
        ItemDesc itemDesc=new ItemDesc();
        itemDesc.setItemId( item.getId());
        itemDesc.setItemDesc(desc);
        itemDesc.setUpdated(item.getUpdated());
        itemDesc.setCreated(item.getUpdated());
        itemDescService.save(itemDesc);

    }

    @Override
    public void updateItem(Item item, String desc) {
        if(item==null|| StringUtils.isEmpty(desc)){
            return;
        }
        //设置修改时间
        item.setUpdated(new Date());
        this.update(item);

        //修改商品描述
        ItemDesc itemDesc=new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        itemDesc.setUpdated(item.getUpdated());
        itemDescService.update(itemDesc);

        //TODO 网络问题会通知失败
        //通知前台系统删除缓存
        String json = HttpClientUtil.doPost("http://front.yufan.com/item/cache/" + item.getId());
        Result result = JsonUtils.jsonToPojo(json,Result.class);
        if (result.getStatus().equals("success")){
            //成功
            LOGGER.debug("通知删除缓存成功！，商品id为:{}",item.getId());
        }
    }


}
