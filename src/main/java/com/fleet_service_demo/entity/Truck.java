package com.fleet_service_demo.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Truck {

    private long id;
    private String truckNumber;
    private String truckType;
    private String status;
    private String location;
    private long mileage;
    private String model;
    private int manufacturingYear;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
