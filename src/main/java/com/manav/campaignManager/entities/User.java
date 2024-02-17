package com.manav.campaignManager.entities;


import com.fasterxml.jackson.annotation.JsonProperty;
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
@Table(name = "user_details")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @JsonProperty(required = false)         // DEFAULT VALUE FOR ALL THE FIELDS
    private Integer userId;

    @Column(name = "first_name")
    @JsonProperty(required = true)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email_id")
    private String emailId;

    @Column(name = "registration_time")
    private String registrationTime;

    @Column(name = "password")
    private String password;
}

