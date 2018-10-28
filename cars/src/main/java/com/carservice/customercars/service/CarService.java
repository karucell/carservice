package com.carservice.customercars.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.carservice.customercars.repository.Brand;
import com.carservice.customercars.repository.CarEntity;
import com.carservice.customercars.repository.CarRepository;

@Service
public class CarService {

    private CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<CarEntity> findAllCars() {
        return carRepository.findAll();
    }

    public Optional<CarEntity> findCarByRegNumber(String regNumber) {
        return carRepository.findCarByRegNumber(regNumber);
    }

    public Optional<CarEntity> findCarByOwnerName(String ownerName) {
        return carRepository.findCarByOwnerName(ownerName);
    }

    public List<CarEntity> findCarsByBrand(Brand brand) {
        return carRepository.findAllByBrand(brand);
    }

    public String addCar(String regNumber, String brandName, String ownerName) {
        CarEntity car = new CarEntity();
        car.setRegNumber(regNumber);
        car.setBrand(Brand.valueOf(brandName));
        car.setOwnerName(ownerName);
        car = carRepository.save(car);
        return car.getId();
    }

}
