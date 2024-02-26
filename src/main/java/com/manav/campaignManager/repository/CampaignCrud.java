package com.manav.campaignManager.repository;

import com.manav.campaignManager.entity.Campaign;
import com.manav.campaignManager.entity.CampaignStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignCrud extends CrudRepository<Campaign, Integer> {
    @Query("SELECT c FROM Campaign c WHERE c.status = :status")
    List<Campaign> findCampaignsByStatus(@Param("status") CampaignStatus status);
}
