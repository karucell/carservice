package com.carservice.customercars.resource;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
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

import com.carservice.customercars.exceptions.EntityNotFoundException;
import com.carservice.customercars.repository.Brand;
import com.carservice.customercars.repository.CarEntity;
import com.carservice.customercars.service.CarService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CarController {

    private CarService carService;
    private Function<String, Supplier<String>> nullParameterSupplier = field -> () -> String.format("Missing field: %s", field);

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/cars")
    public ResponseEntity<List<Car>> findAllCars() {

        List<CarEntity> carEntities = carService.findAllCars();

        List<Car> cars = carEntities
                                 .stream()
                                 .map(Car::toCar)
                                 .collect(Collectors.toList());

        return ResponseEntity.ok(cars);
    }

    @GetMapping("/cars/regnumber/{regnumber}")
    public ResponseEntity<Car> findCarByRegNumber(@PathVariable("regnumber") String regNumber) {
        Objects.requireNonNull(regNumber, nullParameterSupplier.apply("regNumber"));

        Optional<CarEntity> optCarEntity = carService.findCarByRegNumber(regNumber);

        return optCarEntity
                       .map(Car::toCar)
                       .map(ResponseEntity::ok)
                       .orElseThrow(() -> new EntityNotFoundException(regNumber));
    }

    @GetMapping("/cars/ownername/{ownername}")
    public ResponseEntity<Car> findCarByOwnerName(@PathVariable String ownerName) {
        Objects.requireNonNull(ownerName, nullParameterSupplier.apply("ownerName"));

        Optional<CarEntity> optCarEntity = carService.findCarByOwnerName(ownerName);
        return optCarEntity
                       .map(Car::toCar)
                       .map(ResponseEntity::ok)
                       .orElseThrow(() -> new EntityNotFoundException(ownerName));
    }

    @GetMapping("/cars/brand/{brand}")
    public ResponseEntity<List<Car>> findCarByBrand(@PathVariable String brand) {
        Objects.requireNonNull(brand, nullParameterSupplier.apply("brand"));

        List<CarEntity> carEntities = carService.findCarsByBrand(Brand.valueOf(brand));
        List<Car> cars = carEntities
                                 .stream()
                                 .map(Car::toCar)
                                 .collect(Collectors.toList());
        return ResponseEntity.ok(cars);
    }

    @PostMapping("/cars")
    public ResponseEntity<String> addCar(@RequestBody Car car) {
        Objects.requireNonNull(car, nullParameterSupplier.apply("car"));

        String newCarId = carService.addCar(car.getRegNumber(), car.getBrand(), car.getOwnerName());

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
