package com.fleet_service_demo.controller;

import com.fleet_service_demo.dto.APIResponse;
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
    public ResponseEntity<?> getTruckById(@PathVariable long id){
        try {
            TruckResponse truckResponse = truckService.getTruckById(id);
            APIResponse<TruckResponse> response = new APIResponse<>(
                    "Truck retrieved successfully",
                    HttpStatus.OK.value(),
                    truckResponse
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException ex) {
            APIResponse<String> response = new APIResponse<>(
                    ex.getMessage(),
                    HttpStatus.NOT_FOUND.value(),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
    }
