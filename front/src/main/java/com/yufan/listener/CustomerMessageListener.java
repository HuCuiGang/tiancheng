package com.yufan.listener;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.yufan.service.ItemService;

import javax.jms.Message;
import javax.jms.MessageListener;

public class CustomerMessageListener implements MessageListener {

    private Logger LOGGER=LoggerFactory.getLogger(CustomerMessageListener.class);

    @Autowired
    private ItemService itemService;

    @Override
    public void onMessage(Message message) {

        try {
            ActiveMQTextMessage activeMQTextMessage = (ActiveMQTextMessage) message;
            LOGGER.info("接收到的消息为:{}", activeMQTextMessage.getText());
            //删除缓存
            itemService.deleteCache(Long.valueOf(activeMQTextMessage.getText()));
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error("删除缓存失败{}",e.getMessage());
    }

    }

}
