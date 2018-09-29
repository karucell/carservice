package com.carservice.maintenancequeue.resource;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.carservice.maintenancequeue.exceptions.InvalidEntityException;
import com.carservice.maintenancequeue.resource.model.Maintenance;
import com.carservice.maintenancequeue.service.MaintenanceQueueService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("")
@Slf4j
public class MaintenanceQueueController {

    private MaintenanceQueueService maintenanceQueueService;

    public MaintenanceQueueController(MaintenanceQueueService maintenanceQueueService) {
        this.maintenanceQueueService = maintenanceQueueService;
    }

    @PostMapping(path = ResourcePaths.ADD_MAINTENANCE)
    public ResponseEntity<String> addMaintenance(@RequestBody Maintenance request) {
        log.info("addMaintenance: {}", request);
        String maintenanceId = maintenanceQueueService.addMaintenance(
                request.getCarId(),
                request.getProcedureId()
        );
        return ResponseEntity.ok(maintenanceId);
    }

    @PutMapping(path = ResourcePaths.START_MAINTENANCE)
    public ResponseEntity<Maintenance> startMaintenance(@RequestBody String maintenanceId) {
        log.info("startMaintenance: {}", maintenanceId);
        Maintenance maintenance = maintenanceQueueService.startMaintenance(maintenanceId);
        return ResponseEntity.ok(maintenance);
    }

    @PutMapping(path = ResourcePaths.COMPLETE_MAINTENANCE)
    public ResponseEntity<Maintenance> completeMaintenance(@RequestBody String maintenanceId) {
        log.info("completeMaintenance: {}", maintenanceId);
        Maintenance maintenance = maintenanceQueueService.completeMaintenance(maintenanceId);
        return ResponseEntity.ok(maintenance);
    }

    @GetMapping(path = ResourcePaths.FETCH_MAINTENANCES)
    public ResponseEntity<List<Maintenance>> fetchMaintenances() {
        log.info("fetchMaintenances:");
        List<Maintenance> maintenances = maintenanceQueueService.fetchMaintenances();
        return ResponseEntity.ok(maintenances);
    }

    @ControllerAdvice
    class ExceptionsAdvice {

        @ResponseBody
        @ExceptionHandler(InvalidEntityException.class)
        @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
        String unprocessableEntityExceptionHandler(InvalidEntityException ex) {
            return ex.getMessage();
        }

        @ResponseBody
        @ExceptionHandler(Throwable.class)
        @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        String unknownExceptionHandler(Throwable ex) {
            return ex.getMessage();
        }
    }


}
