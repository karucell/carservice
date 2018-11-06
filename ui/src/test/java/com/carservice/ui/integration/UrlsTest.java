package com.carservice.ui.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UrlsTest {

    private Urls urls;

    @BeforeEach
    void setup() {
        urls = new Urls();
    }

    @Test
    void carsHostWithPath_withHostAndPortSet_shouldReturnProperUrl() {
        urls.setHost("host");
        urls.setCarsPort("1234");

        String url = urls.carsHostWithPath.apply("path");

        assertEquals("http://host:1234/path", url);
    }

    @Test
    void carsHostWithPath_withExtraSlashInPath_shouldStripExtraSlash() {
        urls.setHost("host");
        urls.setCarsPort("1234");

        String url = urls.carsHostWithPath.apply("/path");

        assertEquals("http://host:1234/path", url);
    }

}