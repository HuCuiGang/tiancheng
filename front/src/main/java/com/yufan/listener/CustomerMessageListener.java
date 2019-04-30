package com.yufan.listener;

import com.yufan.redis.RedisService;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.yufan.service.ItemService;
import org.springframework.util.StringUtils;

import javax.jms.Message;
import javax.jms.MessageListener;

public class CustomerMessageListener implements MessageListener {

    private Logger LOGGER=LoggerFactory.getLogger(CustomerMessageListener.class);

    @Autowired
    private ItemService itemService;

    @Autowired
    private RedisService redisService;

    @Override
    public void onMessage(Message message) {
        ActiveMQTextMessage activeMQTextMessage = null;
        try {
            activeMQTextMessage = (ActiveMQTextMessage) message;
            LOGGER.info("接收到的消息为:{}", activeMQTextMessage.getText());
            //删除缓存

            //处理在商品减库存的情况下,网络延迟，重复消费。
            String jmsMessageID = activeMQTextMessage.getJMSMessageID();

            //判断消息是否被消费
            String mqValue = redisService.hget("mq", jmsMessageID);
            if(!StringUtils.isEmpty(mqValue)){
                LOGGER.info("该消息已经被消费,再次签收,避免再次重试！jmsMessageID为:{}",jmsMessageID);
                activeMQTextMessage.acknowledge();
            }

            //演示重试机制,签收前发生异常。
            //int i = 1/0;

            itemService.deleteCache(Long.valueOf(activeMQTextMessage.getText()));

            //记录消费
            redisService.hset("mq",jmsMessageID,"true");

            //手动签收
            activeMQTextMessage.acknowledge();
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error("处理消息失败：{}",e.getMessage());
            throw new RuntimeException("处理失败");
        }

    }

}
