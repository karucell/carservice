package com.carservice.cars.resource;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.carservice.cars.common.CarMapper;
import com.carservice.cars.exceptions.EntityNotFoundException;
import com.carservice.cars.repository.Brand;
import com.carservice.cars.repository.CarEntity;
import com.carservice.cars.service.CarService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CarController {

    private Function<String, Supplier<String>> nullParameterSupplier = field -> () -> String.format("Missing field: %s", field);
    private CarService carService;
    private CarMapper carMapper;


    public CarController(CarService carService, CarMapper carMapper) {
        this.carService = carService;
        this.carMapper = carMapper;
    }

    @GetMapping("/cars")
    public ResponseEntity<List<CarDto>> findAllCars() {
        List<CarDto> cars = carService
                                    .findAllCars()
                                    .map(carMapper::toCarDto)
                                    .collect(Collectors.toList());
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/cars/regnumber/{regnumber}")
    public ResponseEntity<CarDto> findCarByRegNumber(@PathVariable("regnumber") String regNumber) {
        Objects.requireNonNull(regNumber, nullParameterSupplier.apply("regNumber"));

        return carService
                       .findCarByRegNumber(regNumber)
                       .map(carMapper::toCarDto)
                       .map(ResponseEntity::ok)
                       .orElseThrow(() -> new EntityNotFoundException(regNumber));
    }

    @GetMapping("/cars/ownername/{ownername}")
    public ResponseEntity<CarDto> findCarByOwnerName(@PathVariable String ownerName) {
        Objects.requireNonNull(ownerName, nullParameterSupplier.apply("ownerName"));

        return carService
                       .findCarByOwnerName(ownerName)
                       .map(carMapper::toCarDto)
                       .map(ResponseEntity::ok)
                       .orElseThrow(() -> new EntityNotFoundException(ownerName));
    }

    @GetMapping("/cars/brand/{brand}")
    public ResponseEntity<List<CarDto>> findCarByBrand(@PathVariable String brand) {
        Objects.requireNonNull(brand, nullParameterSupplier.apply("brand"));

        List<CarDto> cars = carService
                                    .findCarsByBrand(Brand.valueOf(brand))
                                    .map(carMapper::toCarDto)
                                    .collect(Collectors.toList());
        return ResponseEntity.ok(cars);
    }

    @PostMapping("/cars")
    public ResponseEntity<String> addCar(@RequestBody CarDto carDto) {
        Objects.requireNonNull(carDto, nullParameterSupplier.apply("car"));

        CarEntity newCarEntity = new CarEntity();
        carMapper.updateCarFromDto(carDto, newCarEntity);

        String newCarId = carService.addCar(newCarEntity);

        return ResponseEntity.ok(newCarId);
    }

    @ControllerAdvice
    class ExceptionsAdvice {
        @ResponseBody
        @ExceptionHandler(EntityNotFoundException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        String entityNotFoundExceptionHandler(EntityNotFoundException ex) {
            return ex.getMessage();
        }
    }

}
