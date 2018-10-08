package com.carservice.ui.layout;

import org.springframework.beans.factory.annotation.Autowired;

import com.carservice.ui.view.Router;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;

@UIScope
@SpringComponent
public class TopArea extends HorizontalLayout {

    @Autowired
    public TopArea(Router router) {
        addComponent(new Label("Car Service Application"));
        addMenuButton("Queue", router.toQueue);
        addMenuButton("Cars", router.toCars);
        addMenuButton("Procedures", router.toProcedures);
        addMenuButton("Mechanics", router.toMechanics);

        iterator().forEachRemaining(component -> setComponentAlignment(component, Alignment.MIDDLE_LEFT));
    }

    private void addMenuButton(String title, Runnable linkTarget) {
        Button link = new Button(title, clickEvent -> linkTarget.run());
        link.addStyleName(ValoTheme.BUTTON_LINK);
        addComponent(link);
    }

}
