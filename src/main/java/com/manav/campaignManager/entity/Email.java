package com.manav.campaignManager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "email")
public class Email {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column( name = "notification_id")
    private Integer notificationId;
    
    @Column( name = "sent_time", nullable = false)
    private String sentTime;
    
    @Column( name = "subject", nullable = false)
    private String subject;
    
    @Column( name = "body", columnDefinition = "TEXT", nullable = false)
    private String body;

    @Column( name = "user_id", nullable = false)
    private Integer userId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "audience_id", nullable = false)
    private Audience targetAudience;
}
