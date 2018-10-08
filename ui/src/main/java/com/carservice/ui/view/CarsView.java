package com.carservice.ui.view;

import com.carservice.ui.model.Car;
import com.carservice.ui.model.CarBranch;
import com.carservice.ui.service.CarsService;
import com.vaadin.data.Binder;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@UIScope
@SpringView(name = CarsView.VIEW_NAME)
public class CarsView extends HorizontalLayout implements View {

    public static final String VIEW_NAME = "cars";

    private CarsService carsService;
    private Binder<Car> binder;

    private Grid<Car> carGrid;
    private ComboBox<CarBranch> branchSelector;

    public CarsView(CarsService carsService) {
        this.carsService = carsService;
        this.binder = new Binder<>();
        createView();
        updateView();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        updateView();
    }

    private void createView() {
        setMargin(false);
        setSizeFull();
        carGrid = new Grid<>();
        carGrid.getEditor().setEnabled(false);
        carGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        carGrid.addColumn(Car::getRegNumber).setCaption("Reg. number");
        carGrid.addColumn(Car::getOwner).setCaption("Owner");
        carGrid.addColumn(car -> car.getCarBranch().getDescription()).setCaption("Branch");
        carGrid.addSelectionListener(event -> event.getFirstSelectedItem().ifPresent(this::selectCar));
        VerticalLayout carsListLayout = new VerticalLayout(carGrid);

        TextField regNumber = new TextField("Reg. number");
        TextField owner = new TextField("Owner");
        branchSelector = new ComboBox<>("Branch");
        branchSelector.setItemCaptionGenerator(CarBranch::getDescription);

        FormLayout carForm = new FormLayout(
                regNumber,
                owner,
                branchSelector
        );

        Button newCar = new Button("New", event -> this.resetEditor());
        Button saveCar = new Button("Save", event -> this.saveChanges());
        HorizontalLayout actionsLayout = new HorizontalLayout(newCar, saveCar);
        VerticalLayout carEditorLayout = new VerticalLayout(
                carForm,
                actionsLayout
        );

        addComponents(carsListLayout, carEditorLayout);

        binder.forField(regNumber).bind(Car::getRegNumber, Car::setRegNumber);
        binder.forField(owner).bind(Car::getOwner, Car::setOwner);
        binder.forField(branchSelector).bind(Car::getCarBranch, Car::setCarBranch);
    }

    private void updateView() {
        carGrid.setDataProvider(new ListDataProvider<>(carsService.getAllCars()));
        branchSelector.setDataProvider(new ListDataProvider<>(carsService.getAllCarBrances()));
    }

    private void selectCar(Car car) {
        binder.setBean(car);
    }

    private void resetEditor() {
        binder.setBean(new Car());
    }

    private void saveChanges() {
        if (binder.getBean() != null) {
            carsService.storeCar(binder.getBean());
            updateView();
        }
    }

}
