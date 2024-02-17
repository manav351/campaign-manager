package com.manav.campaignManager.exceptionHandler.exceptions;

public class InvalidEmailRequest extends RuntimeException {
    public InvalidEmailRequest(String message) {
        super(message);
    }
    
    public InvalidEmailRequest(String message, Throwable cause) {
        super(message, cause);
    }
    
    public InvalidEmailRequest(Throwable cause) {
        super(cause);
    }
}
