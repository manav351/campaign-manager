package com.manav.campaignManager.repository;

import com.manav.campaignManager.entity.Email;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailCrud extends CrudRepository<Email, Integer> {
}
