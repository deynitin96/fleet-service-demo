package com.fleet_service_demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FleetSummaryResponse {

    private long totalTrucks;
    private long availableTrucks;
    private long rentedTrucks;
    private long maintenanceTrucks;
}
