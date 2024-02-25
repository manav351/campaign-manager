package com.manav.campaignManager.repository;

import com.manav.campaignManager.entity.Audience;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AudienceCrudTest {

    private final AudienceCrud audienceCrud;

    @Autowired
    public AudienceCrudTest(AudienceCrud audienceCrud) {
        this.audienceCrud = audienceCrud;
    }

    @Test
    void testFindAudienceByEmail_AudienceExists() {
        // Given
        Audience audience = Audience.builder()
                .emailId("testUser@yopmail.com")
                .firstName("Test")
                .lastName("User")
                .build();

        audienceCrud.save(audience);

        // When
        Optional<Audience> audienceFromDB = audienceCrud.findAudienceByEmail(audience.getEmailId());

        // Then
        assertThat(audienceFromDB.isPresent()).isTrue();
        assertThat(audienceFromDB.get()).isEqualTo(audience);
    }

    @Test
    void testFindAudienceByID(){
        // Given
        Audience audience = Audience.builder()
                .emailId("testUser2@yopmail.com")
                .firstName("Test")
                .lastName("User")
                .build();

        audienceCrud.save(audience);

        // When
        Optional<Audience> audienceFromDB = audienceCrud.findById(audience.getAudienceId());

        // Then
        assertThat(audienceFromDB.isPresent()).isTrue();
        assertThat(audienceFromDB.get()).isEqualTo(audience);
    }

    @Test
    void testFindAudienceByEmail_AudienceNonExists(){
        // Given
        // When
        Optional<Audience> audienceFromDB = audienceCrud.findAudienceByEmail("testNoUser@yopmail.com");

        // Then
        assertThat(audienceFromDB.isPresent()).isFalse();
    }

    @Test
    void testFindAudienceById_AudienceNonExists(){
        // Given
        // When
        Optional<Audience> audienceFromDB = audienceCrud.findById(200);

        //Then
        assertThat(audienceFromDB.isPresent()).isFalse();
    }

    @Test
    void testSaveAudienceWithAllData(){
        // Given
        Audience audience = Audience.builder()
                .emailId("testUser3@yopmail.com")
                .firstName("Test")
                .lastName("User")
                .build();

        audienceCrud.save(audience);

        // When
        Optional<Audience> audienceFromDB = audienceCrud.findById(audience.getAudienceId());

        // Then
        assertThat(audienceFromDB.isPresent()).isTrue();
        assertThat(audienceFromDB.get()).isEqualTo(audience);
    }

    @Test
    void testSaveAudienceWithMandatoryData(){
        // Given
        Audience audience = Audience.builder()
                .emailId("testUser4@yopmail.com")
                .build();

        audienceCrud.save(audience);

        // When
        Optional<Audience> audienceFromDB = audienceCrud.findById(audience.getAudienceId());

        // Then
        assertThat(audienceFromDB.isPresent()).isTrue();
        assertThat(audienceFromDB.get()).isEqualTo(audience);
    }

    @Test
    void testSaveAudienceWithAlreadyRegisteredEmail(){
        // Given
        Audience audience = Audience.builder()
                .emailId("testUser5@yopmail.com")
                .build();

        audienceCrud.save(audience);

        Audience audience2 = Audience.builder()
                .emailId("testUser5@yopmail.com")
                .build();

        // When
        Throwable throwable = catchThrowable(() -> audienceCrud.save(audience2));

        // Then
        assertThat(throwable).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void testSaveAudienceWithNoMandatoryData(){
        // Given
        Audience audience = Audience.builder().build();

        // When
        Throwable throwable = catchThrowable(() -> audienceCrud.save(audience));

        // Then
        assertThat(throwable).isInstanceOf(DataIntegrityViolationException.class);
    }
}