package com.manav.campaignManager.service;

import com.manav.campaignManager.entity.Audience;
import com.manav.campaignManager.exceptionHandler.exceptions.InvalidAudienceData;
import com.manav.campaignManager.repository.AudienceCrud;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AudienceService {

    final private AudienceCrud audienceCrud;

    public Audience findAudienceById( Integer audienceId ) {
        return audienceCrud.findById(audienceId).orElse(null);
    }

    public Audience findAudienceByEmail( String emailId ){
        Optional<Audience> audience = audienceCrud.findAudienceByEmail(emailId);
        return audience.orElse(null);
    }

    public Audience addAudience( Audience audience ){
        if( audience.getAudienceId() != null && audience.getAudienceId() != 0 )
            throw new InvalidAudienceData("Empty audience object received");

        if(audience.getEmailId() == null || audience.getEmailId().isEmpty() )
            throw new InvalidAudienceData("No email id found");

        if( findAudienceByEmail(audience.getEmailId()) != null )
            throw new InvalidAudienceData("Audience user already exists");

        return audienceCrud.save(audience);
    }
}
