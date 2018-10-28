package com.carservice.queue.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.carservice.queue.integration.procedures.ProceduresRESTClient;
import com.carservice.queue.repository.QueueEntity;
import com.carservice.queue.repository.QueueRepository;
import com.carservice.queue.repository.Priority;
import com.carservice.queue.repository.ProgressStatus;
import com.carservice.queue.resource.model.Maintenance;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class QueueService {

    private QueueRepository queueRepository;
    private ProceduresRESTClient proceduresRESTClient;

    public QueueService(
            QueueRepository queueRepository,
            ProceduresRESTClient proceduresRESTClient
    ) {
        this.queueRepository = queueRepository;
        this.proceduresRESTClient = proceduresRESTClient;
    }

    public String addMaintenance(String carId, String procedureId) {

        Optional<Long> optEstimatedTime = proceduresRESTClient.getEstimatedTimeForMaintenance(procedureId);

        Priority priority = optEstimatedTime.map(Priority::getByEstimatedTime).orElse(Priority.DEFAULT);

        QueueEntity maintenance = new QueueEntity();
        maintenance.setId(null);
        maintenance.setCarId(carId);
        maintenance.setProcedureId(procedureId);
        maintenance.setProgressStatus(ProgressStatus.PENDING);
        maintenance.setPriority(priority);
        maintenance.setAdded(LocalDateTime.now());
        maintenance.setStarted(null);
        maintenance.setCompleted(null);

        maintenance = queueRepository.save(maintenance);

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
