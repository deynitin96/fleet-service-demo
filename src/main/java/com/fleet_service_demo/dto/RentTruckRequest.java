package com.fleet_service_demo.dto;

import lombok.Data;
// DTO used to represent the response of a truck rental request, containing the truck ID and the result code of the rental operation.
@Data
public class RentTruckRequest {

    private long id;
    private int resultCode;
}
