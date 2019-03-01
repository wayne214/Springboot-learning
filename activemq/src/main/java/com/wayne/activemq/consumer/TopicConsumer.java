package com.wayne.activemq.consumer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class TopicConsumer {

    @JmsListener(destination = "wayne.topic")
    public void receiveTopic(String text) {
        System.out.println("TopicConsumer topic msg : "+ text);
    }
}
