package com.fleet_service_demo.exception;

public class TruckNotFoundException extends RuntimeException {
    public TruckNotFoundException(long id) {
        super("Truck not found with ID: " + id);
    }
}
