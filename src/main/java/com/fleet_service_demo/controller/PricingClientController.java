package com.fleet_service_demo.controller;

import com.fleet_service_demo.dto.APIResponse;
import com.fleet_service_demo.dto.PricingRequest;
import com.fleet_service_demo.dto.PricingResponse;
import com.fleet_service_demo.service.PricingClientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/quotes")
public class PricingClientController {

    private final PricingClientService pricingClientService;

    public PricingClientController(PricingClientService pricingClientService) {
        this.pricingClientService = pricingClientService;
    }

    @PostMapping("/calculate")
    public ResponseEntity<APIResponse<PricingResponse>>
    calculateQuote(
            @Valid @RequestBody PricingRequest pricingRequest) {

        APIResponse<PricingResponse> response =
                pricingClientService.calculateQuote(pricingRequest);

        return ResponseEntity.ok(response);
    }
}
