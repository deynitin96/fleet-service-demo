package com.fleet_service_demo.client;

import org.springframework.stereotype.Component;
import com.fleet_service_demo.dto.APIResponse;
import com.fleet_service_demo.dto.PricingRequest;
import com.fleet_service_demo.dto.PricingResponse;
import com.fleet_service_demo.exception.PricingServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class PricingClient {

    private static final ParameterizedTypeReference<
            APIResponse<PricingResponse>
            > RESPONSE_TYPE = new ParameterizedTypeReference<>() {
    };

    private final RestTemplate restTemplate;

    private final String pricingServiceUrl;

    public PricingClient(
            RestTemplate restTemplate,
            @Value("${services.pricing.url}")
            String pricingServiceUrl
    ) {
        this.restTemplate = restTemplate;
        this.pricingServiceUrl = pricingServiceUrl;
    }

    public PricingResponse getQuote(
            PricingRequest pricingRequest
    ) {

        String pricingEndpoint =
                pricingServiceUrl + "/api/v1/pricing/quote";

        HttpEntity<PricingRequest> requestEntity =
                new HttpEntity<>(pricingRequest);

        try {

            ResponseEntity<APIResponse<PricingResponse>>
                    response = restTemplate.exchange(
                    pricingEndpoint,
                    HttpMethod.POST,
                    requestEntity,
                    RESPONSE_TYPE
            );

            APIResponse<PricingResponse> responseBody =
                    response.getBody();

            if (responseBody == null ||
                    responseBody.getData() == null) {

                throw new PricingServiceException(
                        "Pricing Service returned an empty response"
                );
            }

            return responseBody.getData();

        } catch (RestClientException exception) {

            throw new PricingServiceException(
                    "Unable to get quote from Pricing Service",
                    exception
            );
        }
    }
}