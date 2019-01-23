package com.carservice.cars.common;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import com.carservice.cars.repository.CarEntity;
import com.carservice.cars.resource.CarDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CarMapper {


    CarDto toCarDto(CarEntity carEntity);

    void updateCarFromDto(CarDto carDto, @MappingTarget CarEntity carEntity);

    @Mappings({
            @Mapping(target = "id", ignore = true)
    })
    void updateCarFromEntity(CarEntity source, @MappingTarget CarEntity target);

}
