package com.carservice.common.exampledata;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExampleClass {
    private String str;
    private Long number;
    private ExampleEnum type;
    private List<String> strings;
}
