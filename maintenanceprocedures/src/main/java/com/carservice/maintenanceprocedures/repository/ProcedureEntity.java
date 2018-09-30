package com.carservice.maintenanceprocedures.repository;

import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcedureEntity {

    @Id
    private String id;

    private CarBranch carBranch;
    private List<Instruction> instructions;
    private Long totalTimeEstimation;

}
