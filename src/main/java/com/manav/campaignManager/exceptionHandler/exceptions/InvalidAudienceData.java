package com.manav.campaignManager.exceptionHandler.exceptions;

public class InvalidAudienceData extends RuntimeException {
    public InvalidAudienceData(String message) {
        super(message);
    }

    public InvalidAudienceData(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidAudienceData(Throwable cause) {
        super(cause);
    }
}
