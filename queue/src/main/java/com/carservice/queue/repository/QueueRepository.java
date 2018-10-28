package com.carservice.queue.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface QueueRepository extends MongoRepository<QueueEntity, String> {
}