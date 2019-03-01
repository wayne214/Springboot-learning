package com.wayne.activemq.consumer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

// 消息消费者
@Component
public class Consumer {
    @JmsListener(destination = "wayne.queue")
    public void receiveQueue(String text) {
        System.out.println("consumer queue msg: "+ text);
    }
}
