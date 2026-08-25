package com.fleet_service_demo.mapper;

import com.fleet_service_demo.dto.TruckRequest;
import com.fleet_service_demo.dto.TruckResponse;
import com.fleet_service_demo.entity.Truck;
import org.springframework.stereotype.Component;

@Component
public class TruckDtoMapper {

    public Truck toEntity(TruckRequest request) {

        return Truck.builder()
                .truckNumber(request.truckNumber())
                .truckType(request.truckType())
                .status(request.status())
                .location(request.location())
                .mileage(request.mileage())
                .model(request.model())
                .manufacturingYear(request.manufacturingYear())
                .build();
    }

    public TruckResponse toResponse(Truck truck) {

        return new TruckResponse(
                truck.getId(),
                truck.getTruckNumber(),
                truck.getTruckType(),
                truck.getStatus(),
                truck.getLocation(),
                truck.getMileage(),
                truck.getModel(),
                truck.getManufacturingYear()
        );
    }
}