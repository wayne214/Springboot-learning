package com.wayne.activemq.config;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Topic;

@Configuration
public class MqTopicConfig {
    @Bean
    public Topic topic() {
        return new ActiveMQTopic("wayne.topic");
    }
}
