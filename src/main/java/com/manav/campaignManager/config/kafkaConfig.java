package com.manav.campaignManager.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class kafkaConfig {

    @Bean
    public NewTopic emailQueue(){
        return TopicBuilder.name("send_email_queue").build();
    }
}
