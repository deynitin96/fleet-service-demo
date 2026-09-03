package com.fleet_service_demo.exception;

public class PricingServiceException extends RuntimeException{

    public PricingServiceException(String message){
        super(message);
    }

    public PricingServiceException(String message, Throwable cause){
        super(message, cause);
    }
}
