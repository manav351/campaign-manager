package com.manav.campaignManager.repositories;

import com.manav.campaignManager.entities.Email;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailCrud extends CrudRepository<Email, Integer> {
}
