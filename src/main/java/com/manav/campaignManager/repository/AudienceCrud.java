package com.manav.campaignManager.repository;

import com.manav.campaignManager.entity.Audience;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AudienceCrud extends CrudRepository<Audience, Integer> {

    @Query(value = "SELECT a FROM Audience a WHERE a.emailId = ?1")
    Optional<Audience> findAudienceByEmail(String emailId);
}
