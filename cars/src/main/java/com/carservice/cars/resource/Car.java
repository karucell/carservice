package com.carservice.cars.resource;

import com.carservice.cars.repository.CarEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    private String id;
    private String regNumber;
    private String brand;
    private String ownerName;

    public static Car toCar(CarEntity carEntity) {
        Car car = new Car();
        car.setId(carEntity.getId());
        car.setRegNumber(carEntity.getRegNumber());
        car.setBrand(carEntity.getBrand().name());
        car.setOwnerName(carEntity.getOwnerName());
        return car;
    }
}
