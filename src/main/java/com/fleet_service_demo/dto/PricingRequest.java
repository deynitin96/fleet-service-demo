package com.fleet_service_demo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record PricingRequest(
        @NotBlank(message = "Truck type is required!!")
        String truckType,

        @Min(value = 1, message = "Rental days must be at least 1")
        int rentalDays,

        @PositiveOrZero(message = "Estimated miles must be a positive number")
        double estimatedMiles,

        @NotBlank(message = "Location is required!!")
        String location
) {
}
