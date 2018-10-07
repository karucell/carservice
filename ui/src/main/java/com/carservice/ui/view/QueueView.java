package com.carservice.ui.view;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@UIScope
@SpringView(name = QueueView.VIEW_NAME)
public class QueueView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "queue";

    public QueueView() {
        setMargin(false);
        addComponent(new Label("Queue view"));
    }

}
