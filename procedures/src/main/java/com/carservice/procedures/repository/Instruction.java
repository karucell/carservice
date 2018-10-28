package com.carservice.procedures.repository;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Instruction {

    @Id
    private String id;

    private String name;
    private String description;
    private Long estimatedTime;

}
