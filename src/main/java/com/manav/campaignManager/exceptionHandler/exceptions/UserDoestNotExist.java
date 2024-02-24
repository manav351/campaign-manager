package com.manav.campaignManager.exceptionHandler.exceptions;

public class UserDoestNotExist extends RuntimeException{
    public UserDoestNotExist(String message) {
        super(message);
    }
    
    public UserDoestNotExist(String message, Throwable cause) {
        super(message, cause);
    }
    
    public UserDoestNotExist(Throwable cause) {
        super(cause);
    }
}
