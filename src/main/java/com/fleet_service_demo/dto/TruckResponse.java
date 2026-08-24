package com.fleet_service_demo.dto;

public record TruckResponse(

        long id,

        String truckNumber,

        String truckType,

        String status,

        String location,

        long mileage,

        String model,

        int manufacturingYear

) {
}
