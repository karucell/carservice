package com.carservice.ui.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.carservice.ui.integration.CarDTO;
import com.carservice.ui.integration.CarsClient;
import com.carservice.ui.model.Car;
import com.carservice.ui.model.CarBranch;

@Component
public class CarsService {

    // TODO: move to mock rest service, and to carsHostWithPath microservice
    public static final CarBranch TOYOTA = new CarBranch("111", "TOYOTA");
    public static final CarBranch VOLKSWAGEN = new CarBranch("222", "VOLKSWAGEN");

    // TODO: move to mock rest service, and to carsHostWithPath microservice
    private List<CarBranch> carBranches = new ArrayList<>(Arrays.asList(TOYOTA, VOLKSWAGEN));
    // TODO: move to mock rest service, and to carsHostWithPath microservice
    private List<Car> cars = new ArrayList<>(Arrays.asList(
            new Car("1234" ,"ABC-123", "Jaakko Mäkynen", TOYOTA),
            new Car("2345" ,"AAA-111", "Raimo Kinnunen", TOYOTA),
            new Car("3456" ,"BBB-222", "Sami Salo", VOLKSWAGEN),
            new Car("4567" ,"DEF-456", "Jukka Kuustonen", VOLKSWAGEN)
    ));

    private CarsClient carsClient;

    public CarsService(CarsClient carsClient) {
        this.carsClient = carsClient;
    }

    public List<CarBranch> getAllCarBrances() {
        return new ArrayList<>(carBranches);
    }

    public List<Car> getAllCars() {
        return carsClient.fetchAllCars().stream()
                .map(CarDTO::toCar)
                .collect(Collectors.toList());
    }

    public Optional<Car> getCarById(String id) {
        Objects.requireNonNull(id);
        return cars.stream()
                       .filter(car -> car.getId().equals(id))
                       .findFirst();
    }

    public Car storeCar(Car car) {
        Objects.requireNonNull(car);
        Objects.requireNonNull(car.getRegNumber());
        Objects.requireNonNull(car.getOwner());
        Objects.requireNonNull(car.getCarBranch());

        if (car.getId() == null) {
            Car newCar = new Car(
                    UUID.randomUUID().toString(),
                    car.getRegNumber(),
                    car.getOwner(),
                    car.getCarBranch()
            );
            cars.add(newCar);
            return newCar;
        } else {
            Car carToModify = getCarById(car.getId()).get();
            Objects.requireNonNull(carToModify);
            carToModify.setRegNumber(car.getRegNumber());
            carToModify.setOwner(car.getOwner());
            carToModify.setCarBranch(car.getCarBranch());
            return carToModify;
        }
    }

}
