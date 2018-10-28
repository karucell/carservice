package com.carservice.procedures.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.carservice.procedures.service.ProcedureService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ProcedureController {

    private ProcedureService procedureService;

    public ProcedureController(ProcedureService procedureService) {
        this.procedureService = procedureService;
    }

    @GetMapping("/procedures/estimatedtime/{procedureId}")
    public ResponseEntity<ProcedureTimeEstimation> getEstimatedTimeOfProcedure(
            @PathVariable(value="procedureId") String procedureId
    ) {
        Long timeEstimation = procedureService.getTotalTimeEstimation(procedureId);
        if (timeEstimation != null) {
            return ResponseEntity.ok(new ProcedureTimeEstimation(procedureId, timeEstimation));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
