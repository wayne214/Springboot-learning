package com.wayne.rabbitmq.hello;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "hello")
public class HelloRecever {

    @RabbitHandler
    public void process(String hello) {
        System.out.println("receiver :" + hello);
    }
}
