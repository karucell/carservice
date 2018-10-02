package com.carservice.customercars.exceptions;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String entityId) {
        super("Not found by id: "+entityId);
    }
}
