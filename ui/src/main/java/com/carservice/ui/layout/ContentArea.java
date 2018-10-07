package com.carservice.ui.layout;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.VerticalLayout;

@UIScope
@SpringViewDisplay
public class ContentArea extends VerticalLayout implements ViewDisplay {

    public ContentArea() {
        setSizeFull();
        setMargin(false);
    }

    @Override
    public void showView(View view) {
        removeAllComponents();
        addComponent(view.getViewComponent());
    }

}
