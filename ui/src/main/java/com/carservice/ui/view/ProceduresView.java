package com.carservice.ui.view;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@UIScope
@SpringView(name = ProceduresView.VIEW_NAME)
public class ProceduresView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "procedures";

    public ProceduresView() {
        addComponent(new Label("Procedures view"));
    }

}
