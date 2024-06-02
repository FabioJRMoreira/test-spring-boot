package com.example.testspringboot.converters;

import com.example.testspringboot.dto.ActeurDto;
import com.example.testspringboot.model.Acteur;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActeurMapper {
    Acteur dtoToModel(ActeurDto dto);
    ActeurDto modelToDto(Acteur acteur);
}
