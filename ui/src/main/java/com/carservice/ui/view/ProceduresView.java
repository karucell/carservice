package com.carservice.ui.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.carservice.ui.model.CarBranch;
import com.carservice.ui.model.Procedure;
import com.carservice.ui.model.ProcedureInstruction;
import com.carservice.ui.service.CarsService;
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
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.VerticalLayout;

@UIScope
@SpringView(name = ProceduresView.VIEW_NAME)
public class ProceduresView extends HorizontalLayout implements View {

    public static final String VIEW_NAME = "procedures";

    private ProceduresService proceduresService;
    private CarsService carsService;

    private Binder<Procedure> binder;

    private Grid<Procedure> grid;
    private ComboBox<CarBranch> branchSelector;
    private TwinColSelect<ProcedureInstruction> instructionsSelect;

    public ProceduresView(
            ProceduresService proceduresService,
            CarsService carsService
    ) {
        this.proceduresService = proceduresService;
        this.carsService = carsService;
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
        grid.addColumn(procedure -> procedure.getCarBranch().getDescription()).setCaption("Branch");
        grid.addColumn(Procedure::getDescription).setCaption("Description");
        grid.addSelectionListener(event -> event.getFirstSelectedItem().ifPresent(this::selectProcedure));
        VerticalLayout listLayout = new VerticalLayout(grid);

        TextField description = new TextField("Description");
        branchSelector = new ComboBox<>("Branch");
        branchSelector.setItemCaptionGenerator(CarBranch::getDescription);
        instructionsSelect = new TwinColSelect<>("Select Instructions");
        instructionsSelect.setLeftColumnCaption("Selected");
        instructionsSelect.setLeftColumnCaption("Available");
        instructionsSelect.setItemCaptionGenerator(ProcedureInstruction::getDescription);

        FormLayout form = new FormLayout(
                description,
                branchSelector,
                instructionsSelect
        );

        Button actionNew = new Button("New", event -> this.resetEditor());
        Button actionSave = new Button("Save", event -> this.saveChanges());
        HorizontalLayout actions = new HorizontalLayout(actionNew, actionSave);
        VerticalLayout editorLayout = new VerticalLayout(
                form,
                actions
        );

        addComponents(listLayout, editorLayout);

        binder.forField(branchSelector).bind(Procedure::getCarBranch, Procedure::setCarBranch);
        binder.forField(description).bind(Procedure::getDescription, Procedure::setDescription);
        binder.forField(instructionsSelect).bind(getInstructions, setInstructions);
    }

    private void updateView() {
        grid.setDataProvider(new ListDataProvider<>(proceduresService.getAllProcedures()));
        branchSelector.setDataProvider(new ListDataProvider<>(carsService.getAllCarBrances()));
        instructionsSelect.setDataProvider(new ListDataProvider<>(proceduresService.getAllProcedureInstructions()));
    }

    private void selectProcedure(Procedure procedure) {
        binder.setBean(procedure);
    }

    private void resetEditor() {
        binder.setBean(new Procedure());
    }

    private void saveChanges() {
        if (binder.getBean() != null) {
            proceduresService.storeProcedure(binder.getBean());
            updateView();
        }
    }

    private ValueProvider<Procedure, Set<ProcedureInstruction>> getInstructions = procedure -> {
        if (procedure.getInstructions() != null && procedure.getInstructions().size() > 0) {
            return new HashSet<>(procedure.getInstructions());
        } else {
            return Collections.emptySet();
        }
    };

    private Setter<Procedure, Set<ProcedureInstruction>> setInstructions = (procedure, procedureInstructions) -> {
        if (procedure != null) {
            if (procedureInstructions != null && procedureInstructions.size() > 0) {
                procedure.setInstructions(new ArrayList<>(procedureInstructions));
            } else {
                procedure.setInstructions(Collections.emptyList());
            }
        }
    };

}
