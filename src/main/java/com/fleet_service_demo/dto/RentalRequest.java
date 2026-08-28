package com.fleet_service_demo.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record RentalRequest(

        @NotBlank(message = "Customer name is required!!")
        String customerName,

        @NotNull(message = "Rental start date is required!!")
        @Future(message = "Rental start date must be in the future!!")
        LocalDateTime expectedReturnDate
) {
}