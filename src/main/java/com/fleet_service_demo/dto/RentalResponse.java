package com.fleet_service_demo.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record RentalResponse(

        long rentalId,
        long truckId,
        String customerName,
        LocalDateTime rentalStartDate,
        LocalDateTime expectedReturnDate,
        String status
) {
}