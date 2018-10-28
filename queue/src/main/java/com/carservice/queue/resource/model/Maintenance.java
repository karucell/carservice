package com.carservice.queue.resource.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Maintenance {
    private String maintenanceId;
    private String carId;
    private String procedureId;
    private ProgressStatus maintenanceStatus;
}
