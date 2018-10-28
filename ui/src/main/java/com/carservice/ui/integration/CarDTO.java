package com.carservice.ui.integration;

import com.carservice.ui.model.Car;
import com.carservice.ui.model.CarBranch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {

    private String id;
    private String regNumber;
    private String brand;
    private String ownerName;

    public static Car toCar(CarDTO carDTO) {
        Car car = new Car();
        car.setId(carDTO.getId());
        car.setRegNumber(carDTO.getRegNumber());
        car.setCarBranch(new CarBranch("111", "TOYOTA"));
        car.setOwner(carDTO.getOwnerName());
        return car;
    }

}
