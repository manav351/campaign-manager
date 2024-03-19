package com.manav.campaignManager.service;

import com.manav.campaignManager.entity.Email;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class KafkaService {

    private KafkaTemplate<String, Email> kafkaTemplate;
    private EmailService emailService;

    public void sendMessage(Email email){
        kafkaTemplate.send("send-email-queue", email);
    }

    @KafkaListener(id = "email-group", topics = "send-email-queue")
    public void listen(Email email){
        log.info("Message Received" + email.toString());
        emailService.sendEmail(email);
    }

}
