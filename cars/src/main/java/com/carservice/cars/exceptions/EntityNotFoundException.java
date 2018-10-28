package com.carservice.cars.exceptions;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String entityId) {
        super("Not found by id: "+entityId);
    }
}
