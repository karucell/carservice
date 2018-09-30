package com.carservice.maintenanceprocedures.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcedureTimeEstimation {

    private String procedureId;
    private Long estimatedTime;

}
