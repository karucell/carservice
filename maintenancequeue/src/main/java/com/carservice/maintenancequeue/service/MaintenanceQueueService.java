package com.carservice.maintenancequeue.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.carservice.maintenancequeue.resource.Maintenance;

@Service
public class MaintenanceQueueService {

    public List<Maintenance> fetchMaintenances() {
        return null;
    }

    public String addMaintenance(String carId, String procedureId) {
        return null;
    }

    public Maintenance startMaintenance(String maintenanceId) {
        return null;
    }

    public Maintenance completeMaintenance(String maintenanceId) {
        return null;
    }
}
