package com.manav.campaignManager.service;

import com.manav.campaignManager.entity.*;
import com.manav.campaignManager.exceptionHandler.exceptions.ErrorWhileTriggeringCampaign;
import com.manav.campaignManager.exceptionHandler.exceptions.InvalidCampaignPayload;
import com.manav.campaignManager.repository.CampaignCrud;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CampaignService {
    final private CampaignCrud campaignJPA;
    final private EmailService emailService;
    
    public CampaignService(final CampaignCrud campaignJPA, final EmailService emailService) {
        this.campaignJPA = campaignJPA;
        this.emailService = emailService;
    }
    
    public Campaign getCampaign(Integer campaignId) {
        return campaignJPA.findById(campaignId).orElse(null);
    }
    
    public Campaign createCampaign(Campaign campaign) {
        System.out.println(campaign);
        if( campaign.getSubject() == null || campaign.getSubject().isEmpty() )
            throw new InvalidCampaignPayload("subject cannot be empty");

        if( campaign.getBody() == null || campaign.getBody().isEmpty() )
            throw new InvalidCampaignPayload("body cannot be empty");

        if( campaign.getTargetAudience() == null || campaign.getTargetAudience().isEmpty() )
            throw new InvalidCampaignPayload("target audience cannot be empty");

        if( campaign.getIsScheduled() != null && campaign.getIsScheduled()) {
            if (campaign.getScheduledTime() == null || campaign.getScheduledTime().isEmpty())
                throw new InvalidCampaignPayload("schedule time cannot be empty when isScheduled is true");
        } else {
            campaign.setIsScheduled(false);
        }
        campaign.setCreationTime(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        campaign.setStatus(CampaignStatus.DRAFT);
        campaign.setSentCount(0);
        return campaignJPA.save(campaign);
    }
    
    public void triggerCampaign(Integer campaignId) {
        if (campaignId == null || campaignId <= 0)
            throw new ErrorWhileTriggeringCampaign("Invalid campaign id received");
        
        Campaign targetCampaign = campaignJPA.findById(campaignId).orElse(null);
        
        if (targetCampaign == null) throw new ErrorWhileTriggeringCampaign("No campaign present for the campaign id");
        
        if (targetCampaign.getStatus() == CampaignStatus.SENT || targetCampaign.getStatus() == CampaignStatus.FAILED)
            throw new ErrorWhileTriggeringCampaign("Campaign either failed to sent or already sent");
        
        if (targetCampaign.getStatus() == CampaignStatus.SCHEDULED)
            throw new ErrorWhileTriggeringCampaign("Campaign will run at its scheduled time");
        
        if (targetCampaign.getStatus() == CampaignStatus.IN_PROGRESS)
            throw new ErrorWhileTriggeringCampaign("Campaign is running or might encountered an error");
        
        if (targetCampaign.getIsScheduled()) {
            targetCampaign.setStatus(CampaignStatus.SCHEDULED);
            campaignJPA.save(targetCampaign);
            return;
        }
        
        try {
            processCampaign(targetCampaign);
        } catch (Exception e) {
            e.printStackTrace();
            targetCampaign.setStatus(CampaignStatus.FAILED);
            campaignJPA.save(targetCampaign);
            throw new ErrorWhileTriggeringCampaign("Campaign failed to PROCESS");
        }
    }
    
    private void processCampaign(Campaign targetCampaign) {
        targetCampaign.setStatus(CampaignStatus.IN_PROGRESS);
        campaignJPA.save(targetCampaign);
        
        for (String recipient : targetCampaign.getTargetAudience()) {
            Email email = Email.builder()
                    .subject(targetCampaign.getSubject())
                    .body(targetCampaign.getBody())
                    .sentTime(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                    .targetAudience(Audience.builder().emailId(recipient).build())
                    .userId(targetCampaign.getCreatedBy().getUserId())
                    .build();
            emailService.sendEmail(email);
            targetCampaign.setSentCount(targetCampaign.getSentCount() + 1 );
        }
        targetCampaign.setStatus(CampaignStatus.SENT);
        campaignJPA.save(targetCampaign);
    }
    
    @Transactional
    @Scheduled(fixedRate = 5 * 60 * 1000)
    public void processScheduledCampaigns() {
        List<Campaign> scheduledCampaigns = campaignJPA.findCampaignsByStatus(CampaignStatus.SCHEDULED);
        LocalDateTime currentTime = LocalDateTime.now();
        
        for (Campaign campaign : scheduledCampaigns) {
            String scheduleTime = campaign.getScheduledTime();
            LocalDateTime targetDateTime = LocalDateTime.parse(scheduleTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            Duration duration = Duration.between(currentTime, targetDateTime);
            
            if (duration.getSeconds() >= 0 && duration.getSeconds() <= 5 * 60) {
                try {
                    processCampaign(campaign);
                } catch (Exception e) {
                    e.printStackTrace();
                    campaign.setStatus(CampaignStatus.FAILED);
                    campaignJPA.save(campaign);
                    throw new ErrorWhileTriggeringCampaign("Campaign failed to PROCESS");
                }
            }
        }
    }
}
