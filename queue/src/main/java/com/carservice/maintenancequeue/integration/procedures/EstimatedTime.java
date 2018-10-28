package com.carservice.maintenancequeue.integration.procedures;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstimatedTime {
    private String procedureId;
    private Long estimatedTime;
}
