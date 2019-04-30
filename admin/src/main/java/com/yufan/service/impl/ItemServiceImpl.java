package com.yufan.service.impl;

import com.yufan.bean.Item;
import com.yufan.bean.ItemDesc;
import com.yufan.service.ItemDescService;
import com.yufan.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.Date;

@Service
public class ItemServiceImpl extends BaseServiceImpl<Item> implements ItemService {

    private Logger LOGGER= LoggerFactory.getLogger(ItemServiceImpl.class);

    @Autowired
    private JmsTemplate jmsTemplate;

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
        //两种办法
       /* String json = HttpClientUtil.doPost("http://front.yufan.com/item/deleteCache/" + item.getId());
        Result result = JsonUtils.jsonToPojo(json,Result.class);
        if (result.getStatus().equals("success")){
            //成功
            LOGGER.debug("通知删除缓存成功！，商品id为:{}",item.getId());
        }*/
       //消息中间件来做

        jmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage(item.getId() + "");
                LOGGER.info("修改商品发送消息itmeId为：{}",item.getId());
                return textMessage;
            }
        });

    }


}
