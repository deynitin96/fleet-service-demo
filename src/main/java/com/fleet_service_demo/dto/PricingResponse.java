package com.fleet_service_demo.dto;

import java.math.BigDecimal;

public record PricingResponse(
        Long quoteId,

        BigDecimal basePrice,

        BigDecimal mileageCharge,

        BigDecimal discount,

        BigDecimal tax,

        BigDecimal totalPrice,

        String currency
) {
}
