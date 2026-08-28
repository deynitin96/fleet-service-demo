package com.fleet_service_demo.controller;

import com.fleet_service_demo.dto.APIResponse;
import com.fleet_service_demo.dto.RentalRequest;
import com.fleet_service_demo.dto.RentalResponse;
import com.fleet_service_demo.service.RentalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rentals")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    // http://localhost:8081/api/v1/rentals/rentTruck/1
    @PostMapping("/rentTruck/{id}")
    public ResponseEntity<?> rentTruck(@PathVariable Long id,
                                       @Valid @RequestBody RentalRequest rentalRequest) {

        RentalResponse rentalResponse = rentalService.rentTruck(id, rentalRequest);

        APIResponse<RentalResponse> response = new APIResponse<>(
                "Truck rented successfully",
                HttpStatus.CREATED.value(),
                rentalResponse
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
}
}