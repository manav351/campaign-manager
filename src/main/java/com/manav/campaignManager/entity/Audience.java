package com.manav.campaignManager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "audience_details")
public class Audience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "audience_id")
    Integer audienceId;

    @Column(name = "email_id")
    String emailId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    public Audience(Audience targetAudience) {
        this.emailId = targetAudience.emailId;
        this.firstName = targetAudience.firstName;
        this.lastName = targetAudience.lastName;
    }
}
