package com.manav.campaignManager.service;

import com.manav.campaignManager.entity.Audience;
import com.manav.campaignManager.entity.Email;
import com.manav.campaignManager.entity.User;
import com.manav.campaignManager.exceptionHandler.exceptions.AudienceDoesNotExists;
import com.manav.campaignManager.exceptionHandler.exceptions.FailedToSendEmail;
import com.manav.campaignManager.exceptionHandler.exceptions.InvalidEmailRequest;
import com.manav.campaignManager.exceptionHandler.exceptions.UserDoestNotExits;
import com.manav.campaignManager.repository.AudienceCrud;
import com.manav.campaignManager.repository.EmailCrud;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class EmailService {
    
    final private JavaMailSender javaMailSender;

    final private EmailCrud emailCrud;

    final private UserService userService;

    final private AudienceCrud audienceCrud;

    final private AudienceService audienceService;

    @Async
    private void sendEmail(String toEmailId, String subject, String message){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        
        mailMessage.setFrom("manav121999@gmail.com");
        mailMessage.setTo(toEmailId);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        
        javaMailSender.send(mailMessage);
    }
    
    public Email sendEmail(Email email){
        // Validations
        validateAndUpdateUserDetails(email);
        
        try {
            sendEmail(email.getTargetAudience().getEmailId(), email.getSubject(), email.getBody());
        } catch (Exception e) {
            System.out.println(email);
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new FailedToSendEmail("failed to send email with notification id: " + email.getNotificationId());
        }
        
        email.setSentTime(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        emailCrud.save(email);
        return email;
    }
    
    private Audience validateAndUpdateUserDetails(Email email) {
        if (email == null || email.getTargetAudience() == null) {
            throw new InvalidEmailRequest("No email or target audience data present");
        }

        if( userService.findUserById(email.getUserId()) == null )
            throw new InvalidEmailRequest("user id does not exists");
        
        Audience targetAudience;
        if (email.getTargetAudience().getEmailId() != null && !email.getTargetAudience().getEmailId().isEmpty()) {
            Audience audience = audienceService.findAudienceByEmail(email.getTargetAudience().getEmailId());
            if ( audience == null) {
                targetAudience = new Audience(email.getTargetAudience());
                targetAudience = audienceService.addAudience(targetAudience); // Save the audience details to the database
            } else {
                targetAudience = audience;
            }
        } else if (email.getTargetAudience().getAudienceId() != null) {
            targetAudience = audienceService.findAudienceById(email.getTargetAudience().getAudienceId());
            if (targetAudience == null) {
                throw new AudienceDoesNotExists("Audience with ID " + email.getTargetAudience().getAudienceId() + " not found");
            }
        } else {
            throw new InvalidEmailRequest("No audience details present");
        }
        
        email.setTargetAudience(targetAudience);
        return targetAudience;
    }
    
    public List<Email> findNotification(Integer notificationId) {
        return emailCrud.findById(notificationId).map(List::of).orElse(Collections.emptyList());
    }
    
    public List<Email> findAllNotifications() {
        return (List<Email>) emailCrud.findAll();
    }
}
