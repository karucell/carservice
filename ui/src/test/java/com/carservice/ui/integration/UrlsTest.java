package com.carservice.ui.integration;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class UrlsTest {

    private Urls urls;

    @Before
    public void setup() {
        urls = new Urls();
    }

    @Test
    public void carsHostWithPath_withHostAndPortSet_shouldReturnProperUrl() {
        urls.setHost("host");
        urls.setCarsPort("1234");

        String url = urls.carsHostWithPath.apply("path");

        assertEquals("http://host:1234/path", url);
    }

    @Test
    public void carsHostWithPath_withExtraSlashInPath_shouldStripExtraSlash() {
        urls.setHost("host");
        urls.setCarsPort("1234");

        String url = urls.carsHostWithPath.apply("/path");

        assertEquals("http://host:1234/path", url);
    }

}