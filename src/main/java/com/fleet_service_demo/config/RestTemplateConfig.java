package com.fleet_service_demo.config;

import java.time.Duration;

import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

//    @Bean
//    public RestTemplate restTemplate(RestTemplateBuilder builder) {
//        return builder
//                .connectTimeout(Duration.ofSeconds(3))
//                .readTimeout(Duration.ofSeconds(5))
//                .build();
//    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}