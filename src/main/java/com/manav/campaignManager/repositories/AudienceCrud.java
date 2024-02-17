package com.manav.campaignManager.repositories;

import com.manav.campaignManager.entities.Audience;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AudienceCrud extends CrudRepository<Audience, Integer> {
}
