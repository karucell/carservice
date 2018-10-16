package com.carservice.ui.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.carservice.ui.model.Mechanic;

@Component
public class MechanicsService {

    private static final Mechanic james = new Mechanic(
            "m-111",
            "James",
            new ArrayList<>(Arrays.asList(
                    ProceduresService.CHANGE_OILS,
                    ProceduresService.CHANGE_OIL_FILTER
            ))
    );
    private static final Mechanic gerard = new Mechanic(
            "m-222",
            "Gerard",
            new ArrayList<>(Arrays.asList(
                    ProceduresService.CHECK_BREAKS,
                    ProceduresService.CHANGE_BREAK_FLUIDS
            ))
    );
    private static final List<Mechanic> mechanics = new ArrayList<>(Arrays.asList(james, gerard));

    public List<Mechanic> getAllMechanics() {
        return new ArrayList<>(mechanics);
    }

    public Optional<Mechanic> getMechanicById(String id) {
        Objects.requireNonNull(id);
        return mechanics.stream()
                       .filter(mechanic -> mechanic.getId().equals(id))
                       .findFirst();
    }

    public Mechanic store(Mechanic mechanic) {
        Objects.requireNonNull(mechanic);
        Objects.requireNonNull(mechanic.getName());
        Objects.requireNonNull(mechanic.getSkills());

        if (mechanic.getId() == null) {
            Mechanic newMechanic = new Mechanic(
                    UUID.randomUUID().toString(),
                    mechanic.getName(),
                    new ArrayList<>(mechanic.getSkills())
            );
            mechanics.add(newMechanic);
            return newMechanic;
        } else {
            // TODO: should check isPresent of getProcedureById(), if not throw exeption
            Mechanic mechanicToModify = getMechanicById(mechanic.getId()).get();
            Objects.requireNonNull(mechanicToModify);
            mechanicToModify.setName(mechanic.getName());
            mechanicToModify.setSkills(new ArrayList<>(mechanic.getSkills()));
            return mechanicToModify;
        }
    }

}
