package com.manav.campaignManager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "campaigns")
public class Campaign {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "campaign_id")
    private Long campaignId;
    
    @Column(nullable = false, name = "campaign_name")
    private String campaignName;
    
    @Column(nullable = false, name = "subject")
    private String subject;
    
    @Column(nullable = false, columnDefinition = "TEXT",  name = "body")
    private String body;
    
    @ElementCollection
    @CollectionTable(name = "campaign_target_audience", joinColumns = @JoinColumn(name = "campaign_id"))
    @Column(name = "target_email")
    private List<String> targetAudience;
    
    @Column(nullable = false, name = "scheduled_time")
    private String scheduledTime;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "status")
    private CampaignStatus status;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by")
    private User createdBy;
    
    @Column(nullable = false, name = "creation_time")
    private String creationTime;
    
    @Column(nullable = false, name = "is_scheduled")
    private Boolean isScheduled;
}
