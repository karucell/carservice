package com.carservice.maintenanceprocedures.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.carservice.maintenanceprocedures.service.MaintenanceProcedureService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class MaintenanceProcedureController {

    private MaintenanceProcedureService maintenanceProcedureService;

    public MaintenanceProcedureController(MaintenanceProcedureService maintenanceProcedureService) {
        this.maintenanceProcedureService = maintenanceProcedureService;
    }

    @GetMapping("/maintenanceprocedures/estimatedtime/{procedureId}")
    public ResponseEntity<ProcedureTimeEstimation> getEstimatedTimeOfProcedure(
            @PathVariable(value="procedureId") String procedureId
    ) {
        Long timeEstimation = maintenanceProcedureService.getTotalTimeEstimation(procedureId);
        if (timeEstimation != null) {
            return ResponseEntity.ok(new ProcedureTimeEstimation(procedureId, timeEstimation));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
