package com.fleet_service_demo.service;

import com.fleet_service_demo.mapper.TruckMapper;
import org.springframework.stereotype.Service;

@Service
public class TruckService {

    private final TruckMapper truckMapper;

    public TruckService(TruckMapper truckMapper) {
        this.truckMapper = truckMapper;
    }
}
