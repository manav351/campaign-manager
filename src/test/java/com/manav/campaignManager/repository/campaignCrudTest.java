package com.manav.campaignManager.repository;

import com.manav.campaignManager.entity.Audience;
import com.manav.campaignManager.entity.Campaign;
import com.manav.campaignManager.entity.CampaignStatus;
import com.manav.campaignManager.entity.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class campaignCrudTest {

    private static UserCrud userCrud;
    private final CampaignCrud campaignCrud;

    @Autowired
    public campaignCrudTest(CampaignCrud campaignCrud, UserCrud userCrud) {
        this.campaignCrud = campaignCrud;
        this.userCrud = userCrud;
    }

    @Test
    @Transactional
    @Rollback
    void findScheduledCampaignsWithDifferentStatuses() {
        // Given
        User user = User.builder()
                .emailId("campaingCreationUser@yopmail.com")
                .userRole("user")
                .firstName("Marry")
                .lastName("Jane")
                .password("testPassword")
                .build();

        userCrud.save(user);
        Campaign campaign1 = Campaign.builder()
                .campaignName("Scheduled Campaign 1")
                .subject("Hi there")
                .body("This is a test campaign from test repo")
                .targetAudience(Arrays.asList("testUser@yopmail.com", "testUser2@yopmail.com"))
                .isScheduled(true)
                .scheduledTime(LocalDateTime.now().plusHours(4).format(DateTimeFormatter.ISO_DATE_TIME))
                .creationTime(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                .status(CampaignStatus.SCHEDULED)
                .createdBy(user).build();

        Campaign campaign2 = Campaign.builder()
                .campaignName("Scheduled Campaign 2")
                .subject("Hello")
                .body("This is another test campaign from test repo")
                .targetAudience(Arrays.asList("testUser3@yopmail.com", "testUser4@yopmail.com"))
                .isScheduled(false)
                .scheduledTime(LocalDateTime.now().plusHours(6).format(DateTimeFormatter.ISO_DATE_TIME))
                .creationTime(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                .status(CampaignStatus.IN_PROGRESS)
                .createdBy(user).build();

        campaignCrud.saveAll(Arrays.asList(campaign1, campaign2));

        System.out.println( "[TEST]" + campaignCrud.findAll() );

        // When
        List<Campaign> scheduledCampaigns = campaignCrud.findCampaignsByStatus(CampaignStatus.SCHEDULED);
        List<Campaign> inProgressCampaigns = campaignCrud.findCampaignsByStatus(CampaignStatus.IN_PROGRESS);

        // Then
        assertThat(scheduledCampaigns.size() > 0 ).isTrue();
        assertThat(scheduledCampaigns.get(0)).isEqualTo(campaign1);

        assertThat(inProgressCampaigns.size() > 0 ).isTrue();
        assertThat(inProgressCampaigns.get(0)).isEqualTo(campaign2);
    }

    @Test
    @Transactional
    @Rollback
    void findScheduledCampaignsWithNoMatchingStatus() {
        // Given
        User user = User.builder()
                .emailId("campaingCreationUser@yopmail.com")
                .userRole("user")
                .firstName("Marry")
                .lastName("Jane")
                .password("testPassword")
                .build();

        userCrud.save(user);
        Campaign campaign = Campaign.builder()
                .campaignName("Scheduled Campaign")
                .subject("Hi there")
                .body("This is a test campaign from test repo")
                .targetAudience(Arrays.asList("testUser@yopmail.com", "testUser2@yopmail.com"))
                .isScheduled(true)
                .scheduledTime(LocalDateTime.now().plusHours(4).format(DateTimeFormatter.ISO_DATE_TIME))
                .creationTime(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                .status(CampaignStatus.SCHEDULED)
                .createdBy(user).build();

        campaignCrud.save(campaign);

        // When
        List<Campaign> inProgressCampaigns = campaignCrud.findCampaignsByStatus(CampaignStatus.IN_PROGRESS);

        // Then
        assertThat(inProgressCampaigns.size() == 0 ).isTrue();
    }

    @Test
    @Transactional
    @Rollback
    void testSaveCampaignWithTargetAudience() {
        // Given
        User user = User.builder()
                .emailId("campaingCreationUser@yopmail.com")
                .userRole("user")
                .firstName("Marry")
                .lastName("Jane")
                .password("testPassword")
                .build();

        userCrud.save(user);
        Campaign campaign = Campaign.builder()
                .campaignName("Test Campaign")
                .subject("Hi there")
                .body("This is a test campaign from test repo")
                .targetAudience(Arrays.asList("testUser@yopmail.com", "testUser2@yopmail.com"))
                .isScheduled(false)
                .scheduledTime(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                .creationTime(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                .status(CampaignStatus.DRAFT)
                .createdBy(user).build();

        // When
        Campaign savedCampaign = campaignCrud.save(campaign);

        // Then
        assertThat(savedCampaign).isNotNull();
        assertThat(savedCampaign.getCampaignId()).isNotNull();
        assertThat(savedCampaign.getCampaignName()).isEqualTo("Test Campaign");
        assertThat(savedCampaign.getSubject()).isEqualTo("Hi there");
        assertThat(savedCampaign.getBody()).isEqualTo("This is a test campaign from test repo");
        assertThat(savedCampaign.getTargetAudience().contains("testUser@yopmail.com")).isTrue();
        assertThat(savedCampaign.getTargetAudience().contains("testUser2@yopmail.com")).isTrue();
        assertThat(savedCampaign.getIsScheduled()).isFalse();
        assertThat(savedCampaign.getScheduledTime()).isEqualTo(campaign.getScheduledTime());
        assertThat(savedCampaign.getCreationTime()).isNotNull();
        assertThat(savedCampaign.getStatus()).isEqualTo(CampaignStatus.DRAFT);
        assertThat(savedCampaign.getCreatedBy()).isEqualTo(user);
    }
}