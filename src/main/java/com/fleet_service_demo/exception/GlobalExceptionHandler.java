package com.fleet_service_demo.exception;

import com.fleet_service_demo.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TruckNotFoundException.class)
    public ResponseEntity<?> handleTruckNotFoundException(
            TruckNotFoundException ex,
            WebRequest request) {

        ErrorDto errorDto = new ErrorDto(
                ex.getMessage(),
                LocalDateTime.now(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TruckRentalException.class)
    public ResponseEntity<?> handleTruckRentalException(TruckRentalException ex, WebRequest request) {
        ErrorDto errorDto = new ErrorDto(
                ex.getMessage(),
                LocalDateTime.now(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorDto,  HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
        ErrorDto errorDto = new ErrorDto(
                ex.getMessage(),
                LocalDateTime.now(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
