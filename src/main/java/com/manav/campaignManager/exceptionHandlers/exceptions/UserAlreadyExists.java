package com.manav.campaignManager.exceptionHandlers.exceptions;

public class UserAlreadyExists extends RuntimeException{
    public UserAlreadyExists(String message) {
        super(message);
    }
    
    public UserAlreadyExists(String message, Throwable cause) {
        super(message, cause);
    }
    
    public UserAlreadyExists(Throwable cause) {
        super(cause);
    }
}
