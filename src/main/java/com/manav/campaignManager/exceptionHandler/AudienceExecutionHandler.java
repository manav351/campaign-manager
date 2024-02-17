package com.manav.campaignManager.exceptionHandler;

import com.manav.campaignManager.entity.Audience;
import com.manav.campaignManager.entity.GenericResponse;
import com.manav.campaignManager.entity.Status;
import com.manav.campaignManager.entity.User;
import com.manav.campaignManager.exceptionHandler.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AudienceExecutionHandler {
    @ExceptionHandler
    public ResponseEntity<GenericResponse<Audience>> handleException(InvalidAudienceData exc){
        return new ResponseEntity<>(
                GenericResponse.<Audience>builder()
                        .status(Status.builder()
                                .status(false)
                                .message("Operation Failed")
                                .error(exc.getMessage())
                                .build())
                        .data(null)
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }


    @ExceptionHandler
    public ResponseEntity<GenericResponse<Audience>> handleException(AudienceDoesNotExists exc){
        return new ResponseEntity<>(
                GenericResponse.<Audience>builder()
                        .status(Status.builder()
                                .status(false)
                                .message("Operation Failed")
                                .error(exc.getMessage())
                                .build())
                        .data(null)
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }
}
