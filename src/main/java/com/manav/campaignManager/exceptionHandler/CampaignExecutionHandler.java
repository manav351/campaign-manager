package com.manav.campaignManager.exceptionHandler;

import com.manav.campaignManager.entity.Campaign;
import com.manav.campaignManager.entity.GenericResponse;
import com.manav.campaignManager.entity.Status;
import com.manav.campaignManager.exceptionHandler.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CampaignExecutionHandler {
    
    @ExceptionHandler
    public ResponseEntity<GenericResponse<Campaign>> handleException(ErrorWhileTriggeringCampaign exc ){
        String message = exc.getMessage();
        
        return new ResponseEntity<>(
                GenericResponse.<Campaign>builder()
                        .status(Status.builder().status(false).message("Operation Failed").error(message).build())
                        .data(null)
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler
    public ResponseEntity<GenericResponse<Campaign>> handleException(InvalidCampaignPayload exc ){
        String message = exc.getMessage();

        return new ResponseEntity<>(
                GenericResponse.<Campaign>builder()
                        .status(Status.builder().status(false).message("Operation Failed").error(message).build())
                        .data(null)
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }
}
