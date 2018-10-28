package com.carservice.procedures.service;

import org.springframework.stereotype.Service;

@Service
public class ProcedureService {

    public Long getTotalTimeEstimation(String procedureId) {
        return 300L;
    }

}
