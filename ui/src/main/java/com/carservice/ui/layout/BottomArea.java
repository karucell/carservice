package com.carservice.ui.layout;


import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

@UIScope
@SpringComponent
public class BottomArea extends HorizontalLayout {

    public BottomArea() {
        addComponent(new Label("Bottom Area"));
    }

}
