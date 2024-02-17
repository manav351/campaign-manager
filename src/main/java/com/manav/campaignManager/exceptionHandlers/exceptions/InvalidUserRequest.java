package com.manav.campaignManager.exceptionHandlers.exceptions;

public class InvalidUserRequest extends RuntimeException {
    public InvalidUserRequest(String message) {
        super(message);
    }
    
    public InvalidUserRequest(String message, Throwable cause) {
        super(message, cause);
    }
    
    public InvalidUserRequest(Throwable cause) {
        super(cause);
    }
}
