package com.carservice.ui;

import org.springframework.beans.factory.annotation.Autowired;

import com.carservice.ui.layout.MainLayout;
import com.carservice.ui.view.Router;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;

@Theme("valo")
@SpringUI
public class CarServiceUI extends UI {

    private MainLayout baseLayout;
    private Router router;

    @Autowired
    public CarServiceUI(
            MainLayout baseLayout,
            Router router
    ) {
        this.baseLayout = baseLayout;
        this.router = router;
    }

    @Override
    protected void init(VaadinRequest request) {
        setContent(baseLayout);
        router.toHome.run();
    }

}
