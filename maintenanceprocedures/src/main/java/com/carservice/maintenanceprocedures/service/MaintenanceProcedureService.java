package com.carservice.maintenanceprocedures.service;

import org.springframework.stereotype.Service;

@Service
public class MaintenanceProcedureService {

    public Long getTotalTimeEstimation(String procedureId) {
        return 300L;
    }

}
