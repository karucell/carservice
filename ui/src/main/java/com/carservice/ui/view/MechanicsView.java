package com.carservice.ui.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.carservice.ui.model.Mechanic;
import com.carservice.ui.model.ProcedureInstruction;
import com.carservice.ui.service.MechanicsService;
import com.carservice.ui.service.ProceduresService;
import com.vaadin.data.Binder;
import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Setter;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.VerticalLayout;

@UIScope
@SpringView(name = MechanicsView.VIEW_NAME)
public class MechanicsView extends HorizontalLayout implements View {

    public static final String VIEW_NAME = "mechanics";

    private MechanicsService mechanicsService;
    private ProceduresService proceduresService;

    private Binder<Mechanic> binder;
    private Grid<Mechanic> grid;
    private TwinColSelect<ProcedureInstruction> instructionsSelect;

    public MechanicsView(
            MechanicsService mechanicsService,
            ProceduresService proceduresService
    ) {
        this.mechanicsService = mechanicsService;
        this.proceduresService = proceduresService;
        this.binder = new Binder<>();
        createView();
        updateView();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        updateView();
        resetEditor();
    }

    private void createView() {
        setMargin(false);
        setSizeFull();

        grid = new Grid<>();
        grid.getEditor().setEnabled(false);
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.addColumn(Mechanic::getName).setCaption("Name");
        grid.addColumn(Mechanic.skillsDescription).setCaption("Skills");
        grid.addSelectionListener(event -> event.getFirstSelectedItem().ifPresent(this::selectMechanic));
        VerticalLayout listLayout = new VerticalLayout(grid);

        TextField name = new TextField("Name");
        instructionsSelect = new TwinColSelect<>("Select skills");
        instructionsSelect.setLeftColumnCaption("Available");
        instructionsSelect.setRightColumnCaption("Known");
        instructionsSelect.setItemCaptionGenerator(ProcedureInstruction::getDescription);

        FormLayout form = new FormLayout(
                name,
                instructionsSelect
        );

        Button actionNew = new Button("New", event -> this.resetEditor());
        Button actionSave = new Button("Save", event -> this.saveChanges());
        HorizontalLayout actionsLayout = new HorizontalLayout(actionNew, actionSave);
        VerticalLayout editorLayout = new VerticalLayout(
                form,
                actionsLayout
        );

        addComponents(listLayout, editorLayout);

        binder.forField(name).bind(Mechanic::getName, Mechanic::setName);
        binder.forField(instructionsSelect).bind(getInstructions, setInstructions);
    }

    private void updateView() {
        grid.setDataProvider(new ListDataProvider<>(mechanicsService.getAllMechanics()));
        instructionsSelect.setDataProvider(new ListDataProvider<>(proceduresService.getAllProcedureInstructions()));
    }

    private void selectMechanic(Mechanic mechanic) {
        binder.setBean(mechanic);
    }

    private void resetEditor() {
        binder.setBean(new Mechanic());
    }

    private void saveChanges() {
        if (binder.getBean() != null) {
            mechanicsService.store(binder.getBean());
            updateView();
        }
    }

    private ValueProvider<Mechanic, Set<ProcedureInstruction>> getInstructions = mechanic -> {
        if (mechanic.getSkills() != null && mechanic.getSkills().size() > 0) {
            return new HashSet<>(mechanic.getSkills());
        } else {
            return Collections.emptySet();
        }
    };

    private Setter<Mechanic, Set<ProcedureInstruction>> setInstructions = (mechanic, procedureInstructions) -> {
        if (mechanic != null) {
            if (procedureInstructions != null && procedureInstructions.size() > 0) {
                mechanic.setSkills(new ArrayList<>(procedureInstructions));
            } else {
                mechanic.setSkills(Collections.emptyList());
            }
        }
    };

}
