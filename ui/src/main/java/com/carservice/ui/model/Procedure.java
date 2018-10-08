package com.carservice.ui.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Procedure {

    private String id;
    private String description;
    private CarBranch carBranch;
    private List<ProcedureInstruction> instructions;

}
