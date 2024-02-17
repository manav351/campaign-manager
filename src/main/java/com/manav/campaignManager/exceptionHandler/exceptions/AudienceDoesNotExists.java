package com.manav.campaignManager.exceptionHandler.exceptions;

public class AudienceDoesNotExists extends RuntimeException {
    public AudienceDoesNotExists(String message) {
        super(message);
    }

    public AudienceDoesNotExists(String message, Throwable cause) {
        super(message, cause);
    }

    public AudienceDoesNotExists(Throwable cause) {
        super(cause);
    }
}
