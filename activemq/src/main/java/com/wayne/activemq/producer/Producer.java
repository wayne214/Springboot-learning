package com.wayne.activemq.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

// 消息生产者
@Component
public class Producer {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private Queue queue;
    public void sendQueue(String msg) {
        this.jmsMessagingTemplate.convertAndSend(this.queue,msg);
    }
}
