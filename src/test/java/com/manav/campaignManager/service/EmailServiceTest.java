package com.manav.campaignManager.service;

import com.manav.campaignManager.entity.Audience;
import com.manav.campaignManager.entity.Email;
import com.manav.campaignManager.entity.User;
import com.manav.campaignManager.exceptionHandler.exceptions.AudienceDoesNotExists;
import com.manav.campaignManager.exceptionHandler.exceptions.InvalidEmailRequest;
import com.manav.campaignManager.repository.AudienceCrud;
import com.manav.campaignManager.repository.EmailCrud;
import com.manav.campaignManager.repository.UserCrud;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {
    @InjectMocks
    private EmailService emailService;

    @Mock
    private EmailCrud mockEmailCrud;

    @Mock
    private UserService mockUserService;

    @Mock
    private AudienceService mockAudienceService;

    @Test
    void testSendEmail_NullEmail() {
        // When/Then
        assertThatThrownBy(() -> emailService.sendEmail(null))
                .isInstanceOf(InvalidEmailRequest.class)
                .hasMessage("No email or target audience data present");
    }

    @Test
    void testSendEmail_NullTargetAudience() {
        // Given
        Email email = new Email();
        email.setTargetAudience(null);

        // When/Then
        assertThatThrownBy(() -> emailService.sendEmail(email))
                .isInstanceOf(InvalidEmailRequest.class)
                .hasMessage("No email or target audience data present");
    }

    @Test
    void testSendEmail_InvalidCreaterUser() {
        // Given
        Email email = Email.builder()
                .targetAudience(Audience.builder().emailId("testUser@yopmail.com").build())
                .userId(null)
                .build();

        // When
        when(mockUserService.findUserById(null)).thenReturn(null);

        //Then
        assertThatThrownBy(() -> emailService.sendEmail(email))
                .isInstanceOf(InvalidEmailRequest.class)
                .hasMessage("user id does not exists");
    }

    @Test
    void testSendEmail_UnRegisteredTargetAudienceNotHavingEmail() {
        // Given
        User user = User.builder().build();
        Email email = Email.builder()
                .targetAudience(Audience.builder().build())
                .userId(1)
                .build();

        // When
        when(mockUserService.findUserById(1)).thenReturn(user);

        // Then
        assertThatThrownBy(() -> emailService.sendEmail(email))
                .isInstanceOf(InvalidEmailRequest.class)
                .hasMessage("No audience details present");
    }

    @Test
    void testSendEmail_UnRegisteredTargetAudienceHavingInvalidAudienceId() {
        // Given
        User user = User.builder().build();
        Email email = Email.builder()
                .targetAudience(Audience.builder().audienceId(1).build())
                .userId(1)
                .build();

        // When
        when(mockUserService.findUserById(1)).thenReturn(user);
        when(mockAudienceService.findAudienceById(1)).thenReturn(null);

        // Then
        assertThatThrownBy(() -> emailService.sendEmail(email))
                .isInstanceOf(AudienceDoesNotExists.class)
                .hasMessage("Audience with ID 1 not found");
    }

    @Test
    void testFindNotificationById_validReturn(){
        assertThat(email).isEqualTo();
    }
}