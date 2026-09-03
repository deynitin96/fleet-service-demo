package com.fleet_service_demo.exception;

import com.fleet_service_demo.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TruckNotFoundException.class)
    public ResponseEntity<ErrorDto>
    handleTruckNotFoundException(
            TruckNotFoundException exception,
            WebRequest request
    ) {

        ErrorDto errorDto = new ErrorDto(
                exception.getMessage(),
                LocalDateTime.now(),
                request.getDescription(false)
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorDto);
    }

    @ExceptionHandler(TruckRentalException.class)
    public ResponseEntity<ErrorDto>
    handleTruckRentalException(
            TruckRentalException exception,
            WebRequest request
    ) {

        ErrorDto errorDto = new ErrorDto(
                exception.getMessage(),
                LocalDateTime.now(),
                request.getDescription(false)
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorDto);
    }

    @ExceptionHandler(PricingServiceException.class)
    public ResponseEntity<ErrorDto>
    handlePricingServiceException(
            PricingServiceException exception,
            WebRequest request
    ) {

        ErrorDto errorDto = new ErrorDto(
                exception.getMessage(),
                LocalDateTime.now(),
                request.getDescription(false)
        );

        return ResponseEntity
                .status(HttpStatus.BAD_GATEWAY)
                .body(errorDto);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto>
    handleGlobalException(
            Exception exception,
            WebRequest request
    ) {

        ErrorDto errorDto = new ErrorDto(
                exception.getMessage(),
                LocalDateTime.now(),
                request.getDescription(false)
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorDto);
    }
}