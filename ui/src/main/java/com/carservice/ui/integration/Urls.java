package com.carservice.ui.integration;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Urls {

    private String carServiceHost;
    private String carsPort;

    @Value("${car.service.host}")
    public void setHost(String host) {
        this.carServiceHost = host;
    }

    @Value("${car.service.cars.port}")
    public void setCarsPort(String carsPort) {
        this.carsPort = carsPort;
    }

    private Function<String, String> pathWithoutStartingSlash = path ->
        path.startsWith("/") ? path.substring(1) : path;

    private Function<String, Function<String, String>> hostWithPortAndPath = port -> path ->
        String.format("http://%s:%s/%s", carServiceHost, port, path);

    public Function<String, String> carsHostWithPath = path ->
        hostWithPortAndPath.apply(carsPort).apply(pathWithoutStartingSlash.apply(path));

}
