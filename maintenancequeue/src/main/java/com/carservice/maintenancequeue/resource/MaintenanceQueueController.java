package com.carservice.maintenancequeue.resource;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carservice.maintenancequeue.service.MaintenanceQueueService;

@RestController
@RequestMapping("")
public class MaintenanceQueueController {

    private MaintenanceQueueService maintenanceQueueService;

    public MaintenanceQueueController(MaintenanceQueueService maintenanceQueueService) {
        this.maintenanceQueueService = maintenanceQueueService;
    }

    @GetMapping(path = ResourcePaths.FETCH_MAINTENANCES)
    public ResponseEntity<List<Maintenance>> fetchMaintenances() {
        List<Maintenance> maintenances = maintenanceQueueService.fetchMaintenances();
        return ResponseEntity.ok(maintenances);
    }

    @PostMapping(path = ResourcePaths.ADD_MAINTENANCE)
    public ResponseEntity<String> addMaintenance(@RequestBody Maintenance request) {
        String maintenanceId = maintenanceQueueService.addMaintenance(
                request.getCarId(),
                request.getProcedureId()
        );
        return ResponseEntity.ok(maintenanceId);
    }

    @PutMapping(path = ResourcePaths.START_MAINTENANCE)
    public ResponseEntity<Maintenance> startMaintenance(@RequestBody String maintenanceId) {
        Maintenance maintenance = maintenanceQueueService.startMaintenance(maintenanceId);
        return ResponseEntity.ok(maintenance);
    }

    @PutMapping(path = ResourcePaths.COMPLETE_MAINTENANCE)
    public ResponseEntity<Maintenance> completeMaintenance(@RequestBody String maintenanceId) {
        Maintenance maintenance = maintenanceQueueService.completeMaintenance(maintenanceId);
        return ResponseEntity.ok(maintenance);
    }

}
