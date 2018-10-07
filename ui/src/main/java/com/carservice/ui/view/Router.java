package com.carservice.ui.view;

import java.io.Serializable;
import java.util.function.Consumer;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.UI;

@UIScope
@SpringComponent
public class Router implements Serializable {

    private Consumer<String> navigateTo = path -> UI.getCurrent().getNavigator().navigateTo(path);

    public Runnable toHome = () -> navigateTo.accept(HomeView.VIEW_NAME);
    public Runnable toCars = () -> navigateTo.accept(CarsView.VIEW_NAME);
    public Runnable toMechanics = () -> navigateTo.accept(MechanicsView.VIEW_NAME);
    public Runnable toProcedures = () -> navigateTo.accept(ProceduresView.VIEW_NAME);
    public Runnable toQueue = () -> navigateTo.accept(QueueView.VIEW_NAME);

}
