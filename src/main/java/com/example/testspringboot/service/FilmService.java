package com.example.testspringboot.service;

import com.example.testspringboot.converters.FilmMapper;
import com.example.testspringboot.dto.FilmDto;
import com.example.testspringboot.model.Film;
import com.example.testspringboot.repository.FilmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmRepository filmRepository;
    private final FilmMapper filmMapper;

    public FilmDto saveFilm(FilmDto film){
        return filmMapper.modelToDto(filmRepository.save(filmMapper.dtoToModel(film)));
    }

    public List<FilmDto> getAllFilms(){
        return filmRepository.findAll().stream().map(filmMapper::modelToDto).collect(Collectors.toList());
    }

    public FilmDto getFilmById(Long id){
        return filmMapper.modelToDto(filmRepository.findById(id).orElse(null));
    }
}
