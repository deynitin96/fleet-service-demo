package com.fleet_service_demo.service;

import com.fleet_service_demo.dto.RentTruckRequest;
import com.fleet_service_demo.dto.RentalRequest;
import com.fleet_service_demo.dto.RentalResponse;
import com.fleet_service_demo.entity.Rental;
import com.fleet_service_demo.exception.TruckNotFoundException;
import com.fleet_service_demo.exception.TruckRentalException;
import com.fleet_service_demo.mapper.RentalMapper;
import com.fleet_service_demo.mapper.TruckMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class RentalService {

    private final TruckMapper truckMapper;
    private final RentalMapper rentalMapper;

    public RentalService(TruckMapper truckMapper, RentalMapper rentalMapper) {
        this.truckMapper = truckMapper;
        this.rentalMapper = rentalMapper;
    }

    @Transactional
    public RentalResponse rentTruck(
            long truckId,
            RentalRequest request) {

        // 1. Prepare stored procedure request
        RentTruckRequest rentTruckRequest =
                new RentTruckRequest();

        rentTruckRequest.setId(truckId);

        // 2. Execute rent_truck stored procedure
        truckMapper.rentTruck(rentTruckRequest);

        // 3. Get stored procedure result
        Integer resultCode =
                rentTruckRequest.getResultCode();

        // 4. Truck not found
        if (resultCode == 1) {
            throw new TruckNotFoundException(truckId);
        }

        // 5. Truck already rented / unavailable
        if (resultCode == 2) {
            throw new TruckRentalException(
                    "Truck is not available for rental"
            );
        }

        // 6. Create rental record
        Rental rental = new Rental();

        rental.setTruckId((long) truckId);
        rental.setCustomerName(request.customerName());
        rental.setRentalStartDate(LocalDateTime.now());
        rental.setExpectedReturnDate(
                request.expectedReturnDate()
        );
        rental.setStatus("ACTIVE");

        // 7. Insert rental
        int rowsAffected =
                rentalMapper.createRental(rental);

        // 8. Verify INSERT
        if (rowsAffected != 1) {
            throw new TruckRentalException(
                    "Failed to create rental record"
            );
        }

        // 9. Return response
        return new RentalResponse(
                rental.getId(),
                rental.getTruckId(),
                rental.getCustomerName(),
                rental.getRentalStartDate(),
                rental.getExpectedReturnDate(),
                rental.getStatus()
        );
    }
}
