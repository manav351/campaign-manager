package com.manav.campaignManager.controller;

import com.manav.campaignManager.entity.Email;
import com.manav.campaignManager.entity.GenericResponse;
import com.manav.campaignManager.entity.Status;
import com.manav.campaignManager.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/email")
public class EmailController {
    
    final EmailService serviceObj;
    
    public EmailController(final EmailService serviceObj) {
        this.serviceObj = serviceObj;
    }
    
    @GetMapping("/all")
    public ResponseEntity<GenericResponse<Email>> getAllEmailNotification(){
        return new ResponseEntity<>(
                GenericResponse.<Email>builder()
                        .status(Status.builder().status(true).message("Operation Successful").error("").build())
                        .data(serviceObj.findAllNotifications())
                        .build(),
                HttpStatus.OK
        );
    }
    
    @GetMapping({"", "/"})
    public ResponseEntity<GenericResponse<Email>> getEmailNotification(@RequestParam Integer notification_id){
        return new ResponseEntity<>(
                GenericResponse.<Email>builder()
                        .status(Status.builder().status(true).message("Operation Successful").error("").build())
                        .data(serviceObj.findNotification(notification_id))
                        .build(),
                HttpStatus.OK
        );
    }
    
    @PostMapping("/send")
    public ResponseEntity<GenericResponse<Email>> sendEmail(@RequestBody Email email){
        Email sentEmail = serviceObj.sendEmail(email);
        GenericResponse<Email> response = GenericResponse.<Email>builder()
                .status(
                        Status.builder()
                                .status(true)
                                .message("Operation Successful")
                                .error("")
                                .build()
                )
                .data(List.of(sentEmail))
                .build();
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
