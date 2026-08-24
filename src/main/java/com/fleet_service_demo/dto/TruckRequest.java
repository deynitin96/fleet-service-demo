package com.fleet_service_demo.dto;

import jakarta.validation.constraints.*;

public record TruckRequest(

        @NotBlank
        String truckNumber,

        @NotBlank
        String truckType,

        @NotBlank
        String status,

        @NotBlank
        String location,

        @PositiveOrZero
        long mileage,

        @NotBlank
        String model,

        @Min(2000)
        int manufacturingYear

) {
}