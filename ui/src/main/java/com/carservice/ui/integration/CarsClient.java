package com.carservice.ui.integration;

import java.util.List;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CarsClient {

    private RestTemplateBuilder restTemplateBuilder;
    private Urls urls;

    public CarsClient(
            RestTemplateBuilder restTemplateBuilder,
            Urls urls
    ) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.urls = urls;
    }

    public List<CarDTO> fetchAllCars() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<List<CarDTO>> response = restTemplate.exchange(
                urls.carsHostWithPath.apply("/cars"),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CarDTO>>(){});
        return response.getBody();
    }

}
