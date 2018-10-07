package com.carservice.ui.view;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@UIScope
@SpringView(name = MechanicsView.VIEW_NAME)
public class MechanicsView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "mechanics";

    public MechanicsView() {
        addComponent(new Label("Mechanics view"));
    }

}
