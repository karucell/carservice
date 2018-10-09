package com.carservice.ui.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.carservice.ui.model.Procedure;
import com.carservice.ui.model.ProcedureInstruction;

@Component
public class ProceduresService {

    // TODO: move to mock rest service, and to procedures microservice
    private static final ProcedureInstruction CHANGE_OILS = new ProcedureInstruction("PI-123", "CHANGE_OILS", "Change oils", 10L);
    private static final ProcedureInstruction CHANGE_OIL_FILTER = new ProcedureInstruction("PI-124", "CHANGE_OIL_FILTER", "Change oil filter", 8L);
    private static final ProcedureInstruction CHECK_BREAKS = new ProcedureInstruction("PI-125", "CHECK_BREAKS", "Check breaks", 15L);
    private static final ProcedureInstruction CHANGE_BREAK_FLUIDS = new ProcedureInstruction("PI-126", "CHANGE_BREAK_FLUIDS", "Change break fluids", 12L);

    private static final List<ProcedureInstruction> procedureInstructions = new ArrayList<>(Arrays.asList(
            CHANGE_OILS,
            CHANGE_OIL_FILTER,
            CHECK_BREAKS,
            CHANGE_BREAK_FLUIDS
    ));

    // TODO: move to mock rest service, and to procedures microservice
    private static final Procedure TOYOTA_BASIC_SERVICE = new Procedure(
            "P-123",
            "Basic service for Toyota",
            CarsService.TOYOTA,
            Arrays.asList(
                    CHANGE_OILS,
                    CHANGE_OIL_FILTER
            )
    );
    private static final Procedure TOYOTA_HEAVY_SERVICE = new Procedure(
            "P-123",
            "Heavy service for Toyota",
            CarsService.TOYOTA,
            Arrays.asList(
                    CHANGE_OILS,
                    CHANGE_OIL_FILTER,
                    CHECK_BREAKS,
                    CHANGE_BREAK_FLUIDS
            )
    );
    private static final List<Procedure> procedures = new ArrayList<>(Arrays.asList(TOYOTA_BASIC_SERVICE));

    public List<Procedure> getAllProcedures() {
        return new ArrayList<>(procedures);
    }

    public List<ProcedureInstruction> getAllProcedureInstructions() {
        return new ArrayList<>(procedureInstructions);
    }

    public Optional<Procedure> getProcedureById(String id) {
        Objects.requireNonNull(id);
        return procedures.stream()
                       .filter(procedure -> procedure.getId().equals(id))
                       .findFirst();
    }

    public Procedure storeProcedure(Procedure procedure) {
        Objects.requireNonNull(procedure);
        Objects.requireNonNull(procedure.getDescription());
        Objects.requireNonNull(procedure.getCarBranch());
        Objects.requireNonNull(procedure.getInstructions());

        if (procedure.getId() == null) {
            Procedure newProcedure = new Procedure(
                    UUID.randomUUID().toString(),
                    procedure.getDescription(),
                    procedure.getCarBranch(),
                    procedure.getInstructions()
            );
            procedures.add(newProcedure);
            return newProcedure;
        } else {
            Procedure procedureToModify = getProcedureById(procedure.getId()).get();
            Objects.requireNonNull(procedureToModify);
            procedureToModify.setDescription(procedure.getDescription());
            procedureToModify.setCarBranch(procedure.getCarBranch());
            procedureToModify.setInstructions(procedure.getInstructions());
            return procedureToModify;
        }
    }

}
