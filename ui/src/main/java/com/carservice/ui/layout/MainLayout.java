package com.carservice.ui.layout;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.VerticalLayout;

@UIScope
@SpringComponent
public class MainLayout extends VerticalLayout {

    @Autowired
    public MainLayout(
            TopArea topArea,
            ContentArea contentArea,
            BottomArea bottomArea
    ){
        addComponent(topArea);
        addComponent(contentArea);
        addComponent(bottomArea);
        setExpandRatio(topArea, 0);
        setExpandRatio(contentArea, 1);
        setExpandRatio(bottomArea, 0);
        setSizeFull();
    }

}
