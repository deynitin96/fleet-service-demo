package com.fleet_service_demo.dto;

import jakarta.validation.constraints.*;

public record TruckRequest(

        @NotBlank(message = "Truck number is required!!")
        String truckNumber,

        @NotBlank(message = "Truck type is required!!")
        String truckType,

        @NotBlank(message = "Status is required!!")
        String status,

        @NotBlank(message = "Location is required!!")
        String location,

        @NotNull(message = "Mileage is required!!")
        @PositiveOrZero(message = "Mileage must be a positive number")
        long mileage,

        @NotBlank(message = "Model is required!!")
        String model,

        @NotNull(message = "Manufacturing year is required!!")
        @Min(value = 2000, message = "Manufacturing year must be a valid year!!")
        int manufacturingYear

) {
}