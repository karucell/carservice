package com.carservice.ui.view;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@UIScope
@SpringView(name = CarsView.VIEW_NAME)
public class CarsView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "cars";

    public CarsView() {
        setMargin(false);
        addComponent(new Label("Cars view"));
    }

}
