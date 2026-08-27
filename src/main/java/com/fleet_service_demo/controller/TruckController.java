package com.fleet_service_demo.controller;

import com.fleet_service_demo.dto.APIResponse;
import com.fleet_service_demo.dto.FleetSummaryResponse;
import com.fleet_service_demo.dto.TruckRequest;
import com.fleet_service_demo.dto.TruckResponse;
import com.fleet_service_demo.service.TruckService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trucks")
public class TruckController {

    private final TruckService truckService;

    public TruckController(TruckService truckService) {
        this.truckService = truckService;
    }

    //http://localhost:8081/api/v1/trucks/addTruck
    @PostMapping("/addTruck")
    public ResponseEntity<?> addTruck(
            @Valid @RequestBody TruckRequest truckRequest,
            BindingResult result) {

        if (result.hasErrors()) {

            APIResponse<String> response = new APIResponse<>(
                    "Validation failed!!",
                    HttpStatus.BAD_REQUEST.value(),
                    result.getFieldError().getDefaultMessage()
            );

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }

        TruckResponse truckResponse = truckService.addTruck(truckRequest);

        APIResponse<TruckResponse> response = new APIResponse<>(
                "Truck added successfully",
                HttpStatus.CREATED.value(),
                truckResponse
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //http://localhost:8081/api/v1/trucks/getAllTrucks
    @GetMapping("/getAllTrucks")
    public ResponseEntity<?> getAllTrucks() {
        List<TruckResponse> trucks = truckService.getAllTrucks();
        APIResponse<?> response = new APIResponse<>(
                "Trucks retrieved successfully",
                HttpStatus.OK.value(),
                trucks
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // http://localhost:8081/api/v1/trucks/getAvailableTrucks
    @GetMapping("/getAvailableTrucks")
    public ResponseEntity<?> getAvailableTrucks() {
        List<TruckResponse> trucks = truckService.getAvailableTrucks();
        APIResponse<?> response = new APIResponse<>(
                "Available trucks retrieved successfully",
                HttpStatus.OK.value(),
                trucks
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // http://localhost:8081/api/v1/trucks/getTruckById/1
    @GetMapping("/getTruckById/{id}")
    public ResponseEntity<?> getTruckById(@PathVariable long id) {
        TruckResponse truckResponse = truckService.getTruckById(id);
        APIResponse<TruckResponse> response = new APIResponse<>(
                "Truck retrieved successfully",
                HttpStatus.OK.value(),
                truckResponse
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // http://localhost:8081/api/v1/trucks/getTruckByLocation?location=CHENNAI
    @GetMapping("/getTruckByLocation")
    public ResponseEntity<?> getTruckByLocation(@RequestParam String location) {
        List<TruckResponse> truckResponse = truckService.getTruckByLocation(location);
        APIResponse<List<TruckResponse>> response = new APIResponse<>(
                "Trucks retrieved successfully based on Location",
                HttpStatus.OK.value(),
                truckResponse
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // http://localhost:8081/api/v1/trucks/getFleetSummary
    @GetMapping("/getFleetSummary")
    public ResponseEntity<?> getFleetSummary() {
        FleetSummaryResponse fleetSummary = truckService.getFleetSummary();
        APIResponse<FleetSummaryResponse> response = new APIResponse<>(
                "Fleet summary retrieved successfully",
                HttpStatus.OK.value(),
                fleetSummary
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // http://localhost:8081/api/v1/trucks/rentTruck/1
    @PostMapping("/rentTruck/{id}")
    public ResponseEntity<?> rentTruck(@PathVariable long id) {

        TruckResponse truckResponse = truckService.rentTruck(id);

        APIResponse<TruckResponse> response = new APIResponse<>(
                "Truck rented successfully",
                HttpStatus.OK.value(),
                truckResponse
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
