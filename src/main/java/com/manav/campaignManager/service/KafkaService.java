package com.manav.campaignManager.service;

import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KafkaService {

    private KafkaTemplate<String,String> kafkaTemplate;

    public void sendMessage(String message){
        kafkaTemplate.send("send-email-queue", message);
    }

    @KafkaListener(id = "email-group", topics = "send-email-queue")
    public void listen(String message){
        System.out.println("Message Received : " + message);
    }

}
