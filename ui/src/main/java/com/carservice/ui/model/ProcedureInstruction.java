package com.carservice.ui.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcedureInstruction {

    private String id;
    private String description;
    private Long estimatedMinutes;

}
