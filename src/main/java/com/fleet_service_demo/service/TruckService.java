package com.fleet_service_demo.service;

import com.fleet_service_demo.dto.FleetSummaryResponse;
import com.fleet_service_demo.dto.RentTruckRequest;
import com.fleet_service_demo.dto.TruckRequest;
import com.fleet_service_demo.dto.TruckResponse;
import com.fleet_service_demo.entity.Truck;
import com.fleet_service_demo.exception.TruckNotFoundException;
import com.fleet_service_demo.exception.TruckRentalException;
import com.fleet_service_demo.mapper.TruckDtoMapper;
import com.fleet_service_demo.mapper.TruckMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TruckService {

    private final TruckMapper truckMapper;
    private final TruckDtoMapper truckDtoMapper;

    public TruckService(TruckMapper truckMapper, TruckDtoMapper truckDtoMapper) {
        this.truckMapper = truckMapper;
        this.truckDtoMapper = truckDtoMapper;
    }

    public TruckResponse addTruck(TruckRequest request) {
        Truck truck = truckDtoMapper.toEntity(request);
        truckMapper.insert(truck);
        return truckDtoMapper.toResponse(truck);
    }

    public List<TruckResponse> getAllTrucks() {
        List<Truck> trucks = truckMapper.findAll();
        return trucks.stream()
                .map(truckDtoMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<TruckResponse> getAvailableTrucks() {
        List<Truck> trucks = truckMapper.findAvailableTrucks();
        return trucks.stream()
                .map(truckDtoMapper::toResponse)
                .collect(Collectors.toList());
    }

    public TruckResponse getTruckById(long id) {
        Truck truck = truckMapper.findById(id).orElseThrow(() -> new TruckNotFoundException(id));
        return truckDtoMapper.toResponse(truck);
    }

    public List<TruckResponse> getTruckByLocation(String location) {
        List<Truck> trucks = truckMapper.findByLocation(location);
        return trucks.stream()
                .map(truckDtoMapper::toResponse)
                .collect(Collectors.toList());
    }

    public FleetSummaryResponse getFleetSummary() {
        return truckMapper.getFleetSummary();
    }

    public TruckResponse rentTruck(long id) {
        RentTruckRequest rentTruckRequest = new RentTruckRequest();
        rentTruckRequest.setId(id);
        truckMapper.rentTruck(rentTruckRequest);
        int resultCode = rentTruckRequest.getResultCode();

        if (resultCode == 1) {
                    throw new TruckNotFoundException(id);
        } else if (resultCode == 2) {
            throw new TruckRentalException("Truck is not available for rent with ID: " + id);
        } else if (resultCode != 0) {
            throw new IllegalStateException("Unexpected result code for truck rental: " + resultCode);
        }

        return getTruckById(id);
    }

}