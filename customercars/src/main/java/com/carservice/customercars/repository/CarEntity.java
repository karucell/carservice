package com.carservice.customercars.repository;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "cars")
public class CarEntity {

    @Id
    private String id;

    private String regNumber;
    private Brand brand;
    private String ownerName;

}
