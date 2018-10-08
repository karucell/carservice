package com.carservice.ui.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {

    private String id;
    private String regNumber;
    private String owner;
    private CarBranch carBranch;

}
