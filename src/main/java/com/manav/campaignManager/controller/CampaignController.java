package com.manav.campaignManager.controller;

import com.manav.campaignManager.entity.Campaign;
import com.manav.campaignManager.entity.GenericResponse;
import com.manav.campaignManager.entity.Status;
import com.manav.campaignManager.service.CampaignService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/campaign")
public class CampaignController {
    
    final private CampaignService campaignService;
    
    public CampaignController(final CampaignService campaignService) {
        this.campaignService = campaignService;
    }
    
    @GetMapping({"/",""})
    public ResponseEntity<GenericResponse<Campaign>> getCampaignById(@RequestParam Integer campaignId){
        Campaign campaign = campaignService.getCampaign(campaignId);
        GenericResponse<Campaign> response = GenericResponse.<Campaign>builder()
                .status(
                        Status.builder()
                                .status(true)
                                .message("Operation Successful")
                                .error("")
                                .build()
                )
                .data(campaign == null ? Collections.emptyList() : Collections.singletonList(campaign))
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PostMapping("/add")
    public ResponseEntity<GenericResponse<Campaign>> createCampaign(@RequestBody Campaign campaign){
        Campaign updatedCampaign = campaignService.createCampaign(campaign);
        GenericResponse<Campaign> response = GenericResponse.<Campaign>builder()
                .status(
                        Status.builder()
                                .status(true)
                                .message("Operation Successful")
                                .error("")
                                .build()
                )
                .data(campaign == null ? Collections.emptyList() : Collections.singletonList(updatedCampaign))
                .build();
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PostMapping("/trigger")
    public ResponseEntity<GenericResponse<Campaign>> triggerCampaign(@RequestParam(required = false) Integer campaignId){
        campaignService.triggerCampaign(campaignId);
        
        return new ResponseEntity<>(
                GenericResponse.<Campaign>builder()
                        .status( Status.builder().status(true).message("Operation Successful").error("").build())
                        .data(null)
                        .build(),
                HttpStatus.OK);
    }
}
