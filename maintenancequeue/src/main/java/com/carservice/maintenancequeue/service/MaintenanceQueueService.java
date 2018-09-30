package com.carservice.maintenancequeue.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.carservice.maintenancequeue.integration.maintenanceprocedures.MaintenanceProceduresRESTClient;
import com.carservice.maintenancequeue.repository.MaintenanceEntity;
import com.carservice.maintenancequeue.repository.MaintenanceQueueRepository;
import com.carservice.maintenancequeue.repository.Priority;
import com.carservice.maintenancequeue.repository.ProgressStatus;
import com.carservice.maintenancequeue.resource.model.Maintenance;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MaintenanceQueueService {

    private MaintenanceQueueRepository maintenanceQueueRepository;
    private MaintenanceProceduresRESTClient maintenanceProceduresRESTClient;

    public MaintenanceQueueService(
            MaintenanceQueueRepository maintenanceQueueRepository,
            MaintenanceProceduresRESTClient maintenanceProceduresRESTClient
    ) {
        this.maintenanceQueueRepository = maintenanceQueueRepository;
        this.maintenanceProceduresRESTClient = maintenanceProceduresRESTClient;
    }

    public String addMaintenance(String carId, String procedureId) {

        Optional<Long> optEstimatedTime = maintenanceProceduresRESTClient.getEstimatedTimeForMaintenance(procedureId);

        Priority priority = optEstimatedTime.map(Priority::getByEstimatedTime).orElse(Priority.LOW);

        MaintenanceEntity maintenance = new MaintenanceEntity();
        maintenance.setId(null);
        maintenance.setCarId(carId);
        maintenance.setProcedureId(procedureId);
        maintenance.setProgressStatus(ProgressStatus.PENDING);
        maintenance.setPriority(priority);
        maintenance.setAdded(LocalDateTime.now());
        maintenance.setStarted(null);
        maintenance.setCompleted(null);

        maintenance = maintenanceQueueRepository.save(maintenance);

        return maintenance.getId();
    }

    public Maintenance startMaintenance(String maintenanceId) {
        return null;
    }

    public Maintenance completeMaintenance(String maintenanceId) {
        return null;
    }

    public List<Maintenance> fetchMaintenances() {
        return null;
    }

}
