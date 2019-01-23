package com.carservice.cars.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.carservice.cars.repository.Brand;
import com.carservice.cars.repository.CarEntity;
import com.carservice.cars.repository.CarRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CarServiceTest {

    @Autowired
    CarService carService;

    @Autowired
    CarRepository carRepository;

    @Test
    public void findAllCars_shouldReturnAllCars() {
        String anyValidCarRegNumber = "AAA-111";
        CarEntity existingCar = new CarEntity(null, anyValidCarRegNumber, Brand.TOYOTA, "AnyValidOwner");
        carRepository.save(existingCar);
        long carCount = carRepository.count();

        List<CarEntity> cars = carService.findAllCars().collect(Collectors.toList());

        assertNotNull(cars);
        assertEquals(carCount, cars.size());
    }

    @Test
    public void findCarByRegNumber_withNonExistingRegNumber_shouldReturnEmptyOptional() {
        String anyNonExistingRegNumber = "AABBCC123";

        Optional<CarEntity> car = carService.findCarByRegNumber(anyNonExistingRegNumber);

        assertFalse(car.isPresent());
    }

    @Test
    public void findCarByRegNumber_withExistingRegNumber_shouldReturnCarOptional() {
        String existingCarRegNumber = "ABC-123";
        CarEntity existingCar = new CarEntity(null, existingCarRegNumber, Brand.TOYOTA, "AnyValidOwner");
        existingCar = carRepository.save(existingCar);

        Optional<CarEntity> car = carService.findCarByRegNumber(existingCarRegNumber);

        assertTrue(car.isPresent());
        assertEquals(existingCar.getId(), car.get().getId());
        assertEquals(existingCar.getRegNumber(), car.get().getRegNumber());
    }

    @Test
    public void findCarByOwnerName() {
        // TODO
    }

    @Test
    public void findCarsByBrand() {
        // TODO
    }

    @Test
    public void addCar() {
        // TODO
    }
}