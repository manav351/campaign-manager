package com.manav.campaignManager.repository;

import com.manav.campaignManager.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCrud extends CrudRepository<User, Integer> {
    @Query(value = "SELECT e FROM User e WHERE e.emailId = ?1")
    Optional<User> findByEmailId(String emailId);
}
