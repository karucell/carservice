package com.carservice.maintenancequeue.repository;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "maintenance")
public class MaintenanceEntity {

    @Id
    public String id;

    private String serviceId;
    private String carId;
    private String procedureId;
    private ProgressStatus serviceStatus;

    private LocalDateTime added;
    private LocalDateTime started;
    private LocalDateTime completed;
}
