package com.carservice.cars.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CarRepository extends MongoRepository<CarEntity, String> {

    Optional<CarEntity> findCarByRegNumber(String regNumber);

    Optional<CarEntity> findCarByOwnerName(String ownerName);

    List<CarEntity> findAllByBrand(Brand brand);
}
