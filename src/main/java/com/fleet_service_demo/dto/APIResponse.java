package com.fleet_service_demo.dto;

// Add lombok annotations to generate getters, setters, constructors, and builder pattern
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class APIResponse<T> {

    private String message;
    private int statusCode;
    private T data;
}
