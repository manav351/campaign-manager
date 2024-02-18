package com.manav.campaignManager.exceptionHandler.exceptions;

public class InvalidCampaignPayload extends RuntimeException {
    public InvalidCampaignPayload(String message) {
        super(message);
    }

    public InvalidCampaignPayload(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCampaignPayload(Throwable cause) {
        super(cause);
    }
}
