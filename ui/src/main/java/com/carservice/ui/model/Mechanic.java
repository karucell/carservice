package com.carservice.ui.model;

import java.util.List;

import com.vaadin.data.ValueProvider;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mechanic {

    private String id;
    private String name;
    private List<ProcedureInstruction> skills;

    public static ValueProvider<Mechanic, String> skillsDescription
            = mechanic -> mechanic.getSkills()
                                  .stream()
                                  .map(ProcedureInstruction::getDescription)
                                  .reduce((s1, s2) -> s1 + ", "+ s2)
                                  .orElse("");

}
