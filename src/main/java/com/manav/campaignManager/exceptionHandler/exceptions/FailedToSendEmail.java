package com.manav.campaignManager.exceptionHandler.exceptions;

public class FailedToSendEmail extends RuntimeException {
    public FailedToSendEmail(String message) {
        super(message);
    }
    
    public FailedToSendEmail(String message, Throwable cause) {
        super(message, cause);
    }
    
    public FailedToSendEmail(Throwable cause) {
        super(cause);
    }
}
