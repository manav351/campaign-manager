package com.manav.campaignManager.exceptionHandler;

import com.manav.campaignManager.entity.GenericResponse;
import com.manav.campaignManager.entity.Status;
import com.manav.campaignManager.entity.User;
import com.manav.campaignManager.exceptionHandler.exceptions.InvalidUserRequest;
import com.manav.campaignManager.exceptionHandler.exceptions.UserAlreadyExists;
import com.manav.campaignManager.exceptionHandler.exceptions.UserDoestNotExits;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExecutionHandler {
    
    @ExceptionHandler
    public ResponseEntity<GenericResponse<User>> handleException(UserAlreadyExists exc){
        return new ResponseEntity<>(
                GenericResponse.<User>builder()
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
    public ResponseEntity<GenericResponse<User>> handleException(InvalidUserRequest exc){
        return new ResponseEntity<>(
                GenericResponse.<User>builder()
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
    public ResponseEntity<GenericResponse<User>> handleException(UserDoestNotExits exc){
        return new ResponseEntity<>(
                GenericResponse.<User>builder()
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
