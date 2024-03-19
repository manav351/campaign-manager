package com.manav.campaignManager.service;

import com.manav.campaignManager.entity.Email;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KafkaService {

    private KafkaTemplate<String, Email> kafkaTemplate;

    public void sendMessage(Email email){
        kafkaTemplate.send("send-email-queue", email);
    }

    @KafkaListener(id = "email-group", topics = "send-email-queue")
    public void listen(Email email){
        System.out.println("Message Received : " + email.toString());
    }

}
