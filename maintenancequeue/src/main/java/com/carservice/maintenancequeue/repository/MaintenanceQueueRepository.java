package com.carservice.maintenancequeue.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MaintenanceQueueRepository extends MongoRepository<MaintenanceEntity, String> {
}