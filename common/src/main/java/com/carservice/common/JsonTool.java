package com.carservice.common;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JsonTool {

    private ObjectMapper objectMapper;

    @Autowired
    public JsonTool(ObjectMapper objectMapper1) {
        this.objectMapper = objectMapper1;
    }

    public String toJson(Object data) throws IOException {
        return this.objectMapper.writeValueAsString(data);
    }

    public <T> T fromJson(String jsonString, Class<T> type) throws IOException {
        return this.objectMapper.readValue(jsonString, type);
    }

}
