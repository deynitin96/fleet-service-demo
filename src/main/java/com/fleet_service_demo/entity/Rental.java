package com.fleet_service_demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rental {

    private Long id;

    private Long truckId;

    private String customerName;

    private LocalDateTime rentalStartDate;

    private LocalDateTime expectedReturnDate;

    private LocalDateTime actualReturnDate;

    private String status;

    private LocalDateTime createdAt;
}
