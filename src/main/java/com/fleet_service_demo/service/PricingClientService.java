package com.fleet_service_demo.service;

import com.fleet_service_demo.dto.APIResponse;
import com.fleet_service_demo.dto.PricingRequest;
import com.fleet_service_demo.dto.PricingResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PricingClientService {

    private final RestTemplate restTemplate;
    private final String pricingServiceUrl;

    public PricingClientService(RestTemplate restTemplate,  @Value("${services.pricing.url}") String pricingServiceUrl) {
        this.restTemplate = restTemplate;
        this.pricingServiceUrl = pricingServiceUrl;
    }

    public APIResponse<PricingResponse> calculateQuote(PricingRequest pricingRequest) {
            HttpEntity<PricingRequest> httpEntity = new HttpEntity<>(pricingRequest);

            // Fleet Service Calls the Pricing Service API
            ResponseEntity<APIResponse<PricingResponse>> response = restTemplate.exchange(
                    pricingServiceUrl + "/quote",
                    HttpMethod.POST,
                    httpEntity,
                    new ParameterizedTypeReference<APIResponse<PricingResponse>>() {
                    }
            );

            APIResponse<PricingResponse> responseBody = response.getBody();

            if (responseBody == null || responseBody.getData() == null) {
            throw new IllegalStateException("Pricing service returned an empty response");
        }
        return responseBody;
    }
}
