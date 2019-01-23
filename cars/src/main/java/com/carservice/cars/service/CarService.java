package com.carservice.cars.service;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.carservice.cars.common.CarMapper;
import com.carservice.cars.repository.Brand;
import com.carservice.cars.repository.CarEntity;
import com.carservice.cars.repository.CarRepository;

@Service
public class CarService {

    private CarMapper carMapper;
    private CarRepository carRepository;

    public CarService(
            CarMapper carMapper,
            CarRepository carRepository
    ) {
        this.carMapper = carMapper;
        this.carRepository = carRepository;
    }

    public Stream<CarEntity> findAllCars() {
        return carRepository.findAll().stream();
    }

    public Optional<CarEntity> findCarByRegNumber(String regNumber) {
        return carRepository.findCarByRegNumber(regNumber);
    }

    public Optional<CarEntity> findCarByOwnerName(String ownerName) {
        return carRepository.findCarByOwnerName(ownerName);
    }

    public Stream<CarEntity> findCarsByBrand(Brand brand) {
        return carRepository.findAllByBrand(brand).stream();
    }

    public String addCar(CarEntity carEntity) {
        CarEntity newCarEntity = new CarEntity();
        carMapper.updateCarFromEntity(newCarEntity, carEntity);
        carEntity = carRepository.save(newCarEntity);
        return carEntity.getId();
    }

}
