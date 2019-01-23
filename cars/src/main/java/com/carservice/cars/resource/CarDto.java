package com.carservice.cars.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDto {
    private String id;
    private String regNumber;
    private String brand;
    private String ownerName;
}
