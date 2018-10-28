package com.carservice.queue.repository;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "queue")
public class QueueEntity {

    @Id
    public String id;

    private String carId;
    private String procedureId;
    private ProgressStatus progressStatus;

    private Priority priority;

    private LocalDateTime added;
    private LocalDateTime started;
    private LocalDateTime completed;
}
