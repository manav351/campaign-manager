package com.manav.campaignManager.repository;

import com.manav.campaignManager.entity.Audience;
import com.manav.campaignManager.entity.Email;
import org.antlr.v4.runtime.misc.LogManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class EmailCrudTest {

    private final EmailCrud emailCrud;
    private final AudienceCrud audienceCrud;

    @Autowired
    public EmailCrudTest(EmailCrud emailCrud, AudienceCrud audienceCrud) {
        this.emailCrud = emailCrud;
        this.audienceCrud = audienceCrud;
    }

    @Test
    void saveDetailsOfValidEmail() {
        // Given
        Audience audience = Audience.builder()
                .emailId("tesUser8@yopmail.com")
                .firstName("Test")
                .lastName("User")
                .build();

        audienceCrud.save(audience);

        Email email = Email.builder()
                .sentTime(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                .subject("Test Email")
                .body("This is a test email from JUNIT")
                .userId(1)
                .targetAudience(audience)
                .build();

        // When
        emailCrud.save(email);
        Optional<Email> emailDetailsFromDB = emailCrud.findById(email.getNotificationId());

        // Then
        assertThat(emailDetailsFromDB.isPresent()).isTrue();
        assertThat(email).isEqualTo(emailDetailsFromDB.get());
    }

    @Test
    void retrieveNonExistentEmail() {
        // Given
        Integer nonExistentEmailId = 100;

        // When
        Optional<Email> emailDetailsFromDB = emailCrud.findById(nonExistentEmailId);

        // Then
        assertThat(emailDetailsFromDB.isPresent()).isFalse();
    }

    @Test
    void saveEmailWithNullSentTime() {
        // Given
        Email email = Email.builder()
                .sentTime(null)
                .subject("Test Email")
                .body("This is a test email from JUNIT")
                .userId(1)
                .build();

        // When/Then
        assertThrows(DataIntegrityViolationException.class, () -> emailCrud.save(email));
    }

    @Test
    void testRetrieveExistingEmailById() {
        // Given
        Audience audience = Audience.builder()
                .emailId("tesUser8@yopmail.com")
                .firstName("Test")
                .lastName("User")
                .build();

        audienceCrud.save(audience);

        Email email = Email.builder()
                .sentTime(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                .subject("Test Email")
                .body("This is a test email from JUNIT")
                .userId(1)
                .targetAudience(audience)
                .build();
        emailCrud.save(email);

        // When
        Optional<Email> emailDetailsFromDB = emailCrud.findById(email.getNotificationId());

        // Then
        assertThat(emailDetailsFromDB.isPresent()).isTrue();
        assertThat(email).isEqualTo(emailDetailsFromDB.get());
    }

    @Test
    void testRetrieveNonExistentEmailById() {
        // Given
        Integer nonExistentNotificationId = 1000;

        // When
        Optional<Email> emailDetailsFromDB = emailCrud.findById(nonExistentNotificationId);

        // Then
        assertThat(emailDetailsFromDB.isPresent()).isFalse();
    }
}