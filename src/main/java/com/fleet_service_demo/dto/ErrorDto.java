package com.fleet_service_demo.dto;

import java.time.LocalDateTime;
import java.util.Date;

// Add lombok annotations to generate getters, setters, and constructors
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDto {

    private String message;
    private LocalDateTime date;
    private String uri;
}
