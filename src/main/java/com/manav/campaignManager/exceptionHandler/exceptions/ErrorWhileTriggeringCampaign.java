package com.manav.campaignManager.exceptionHandler.exceptions;

public class ErrorWhileTriggeringCampaign extends RuntimeException {
    public ErrorWhileTriggeringCampaign(String message) {
        super(message);
    }
    
    public ErrorWhileTriggeringCampaign(String message, Throwable cause) {
        super(message, cause);
    }
    
    public ErrorWhileTriggeringCampaign(Throwable cause) {
        super(cause);
    }
}
