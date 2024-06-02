package com.example.testspringboot.converters;

import com.example.testspringboot.dto.FilmDto;
import com.example.testspringboot.model.Film;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FilmMapper {


    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "titre", source = "dto.titre")
    @Mapping(target = "description", source = "dto.description")
    Film dtoToModel(FilmDto dto);
    FilmDto modelToDto(Film film);
}
