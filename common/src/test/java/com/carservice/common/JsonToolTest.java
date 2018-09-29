package com.carservice.common;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.carservice.common.exampledata.ExampleClass;
import com.carservice.common.exampledata.ExampleEnum;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestContextConfiguration.class})
public class JsonToolTest {

    @Autowired
    private JsonTool jsonTool;

    @Test
    public void toJson() throws IOException {
        ExampleClass demoObj = new ExampleClass("str", 123L, ExampleEnum.VALUE1, Arrays.asList("str1", "str2"));
        String expectedJson = "{\"str\":\"str\",\"number\":123,\"type\":\"VALUE1\",\"strings\":[\"str1\",\"str2\"]}";

        String json = jsonTool.toJson(demoObj);

        assertEquals(expectedJson, json);
    }

    @Test
    public void fromJson() throws IOException {
        String json = "{\"str\":\"AAA\",\"number\":456,\"type\":\"VALUE2\",\"strings\":[\"EEE\",\"FFF\"]}";
        ExampleClass expectedObj = new ExampleClass("AAA", 456L, ExampleEnum.VALUE2, Arrays.asList("EEE", "FFF"));

        ExampleClass object = jsonTool.fromJson(json, ExampleClass.class);

        assertEquals(object, expectedObj);
    }

}