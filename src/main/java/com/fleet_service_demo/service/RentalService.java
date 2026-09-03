package com.fleet_service_demo.service;

import com.fleet_service_demo.client.PricingClient;
import com.fleet_service_demo.dto.PricingRequest;
import com.fleet_service_demo.dto.PricingResponse;
import com.fleet_service_demo.dto.RentTruckRequest;
import com.fleet_service_demo.dto.RentalRequest;
import com.fleet_service_demo.dto.RentalResponse;
import com.fleet_service_demo.entity.Rental;
import com.fleet_service_demo.entity.Truck;
import com.fleet_service_demo.exception.TruckNotFoundException;
import com.fleet_service_demo.exception.TruckRentalException;
import com.fleet_service_demo.mapper.RentalMapper;
import com.fleet_service_demo.mapper.TruckMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class RentalService {

    private final TruckMapper truckMapper;

    private final RentalMapper rentalMapper;

    private final PricingClient pricingClient;

    public RentalService(
            TruckMapper truckMapper,
            RentalMapper rentalMapper,
            PricingClient pricingClient
    ) {
        this.truckMapper = truckMapper;
        this.rentalMapper = rentalMapper;
        this.pricingClient = pricingClient;
    }

    @Transactional
    public RentalResponse rentTruck(
            long truckId,
            RentalRequest request
    ) {

        /*
         * Step 1:
         * Get the truck before updating its status.
         *
         * The truck type and location are needed by
         * Pricing Service.
         */
        Truck truck = truckMapper.findById(truckId)
                .orElseThrow(
                        () -> new TruckNotFoundException(truckId)
                );

        /*
         * Step 2:
         * Calculate the rental duration.
         */
        LocalDateTime rentalStartDate =
                LocalDateTime.now();

        long rentalHours = ChronoUnit.HOURS.between(
                rentalStartDate,
                request.expectedReturnDate()
        );

        /*
         * Round partial days upward.
         *
         * Example:
         * 25 hours becomes 2 rental days.
         */
        int rentalDays = Math.max(
                1,
                (int) Math.ceil(rentalHours / 24.0)
        );

        /*
         * Step 3:
         * Prepare the request for Pricing Service.
         */
        PricingRequest pricingRequest =
                new PricingRequest(
                        truck.getTruckType(),
                        rentalDays,
                        request.estimatedMiles(),
                        truck.getLocation()
                );

        /*
         * Step 4:
         * Synchronous RestTemplate call.
         *
         * This method waits until Pricing Service
         * returns the calculated quote.
         */
        PricingResponse pricingResponse =
                pricingClient.getQuote(pricingRequest);

        /*
         * Step 5:
         * Prepare stored procedure request.
         */
        RentTruckRequest rentTruckRequest =
                new RentTruckRequest();

        rentTruckRequest.setId(truckId);

        /*
         * Step 6:
         * Execute rent_truck stored procedure.
         */
        truckMapper.rentTruck(rentTruckRequest);

        Integer resultCode =
                rentTruckRequest.getResultCode();

        /*
         * Step 7:
         * Handle stored procedure result.
         */
        if (resultCode == 1) {
            throw new TruckNotFoundException(truckId);
        }

        if (resultCode == 2) {
            throw new TruckRentalException(
                    "Truck is not available for rental"
            );
        }

        if (resultCode != 0) {
            throw new TruckRentalException(
                    "Unexpected result while renting the truck"
            );
        }

        /*
         * Step 8:
         * Create the rental record.
         */
        Rental rental = new Rental();

        rental.setTruckId(truckId);
        rental.setCustomerName(request.customerName());
        rental.setRentalStartDate(rentalStartDate);
        rental.setExpectedReturnDate(
                request.expectedReturnDate()
        );
        rental.setStatus("ACTIVE");

        /*
         * Step 9:
         * Save the rental record.
         */
        int rowsAffected =
                rentalMapper.createRental(rental);

        if (rowsAffected != 1) {
            throw new TruckRentalException(
                    "Failed to create rental record"
            );
        }

        /*
         * Step 10:
         * Return both rental and pricing details.
         */
        return new RentalResponse(
                rental.getId(),
                rental.getTruckId(),
                rental.getCustomerName(),
                rental.getRentalStartDate(),
                rental.getExpectedReturnDate(),
                rental.getStatus(),
                pricingResponse
        );
    }
}