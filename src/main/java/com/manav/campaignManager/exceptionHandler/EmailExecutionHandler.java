package com.manav.campaignManager.exceptionHandler;

import com.manav.campaignManager.entity.Email;
import com.manav.campaignManager.entity.GenericResponse;
import com.manav.campaignManager.entity.Status;
import com.manav.campaignManager.exceptionHandler.exceptions.FailedToSendEmail;
import com.manav.campaignManager.exceptionHandler.exceptions.InvalidEmailRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EmailExecutionHandler {
    @ExceptionHandler
    public ResponseEntity<GenericResponse<Email>> handleException(InvalidEmailRequest exc ){
        return new ResponseEntity<>(
                GenericResponse.<Email>builder()
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
    public ResponseEntity<GenericResponse<Email>> handleException(FailedToSendEmail exc){
        return new ResponseEntity<>(
                GenericResponse.<Email>builder()
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
