package com.manav.campaignManager.exceptionHandlers.exceptions;

public class UserDoestNotExits extends RuntimeException{
    public UserDoestNotExits(String message) {
        super(message);
    }
    
    public UserDoestNotExits(String message, Throwable cause) {
        super(message, cause);
    }
    
    public UserDoestNotExits(Throwable cause) {
        super(cause);
    }
}
