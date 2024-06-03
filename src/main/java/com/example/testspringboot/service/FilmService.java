package com.example.testspringboot.service;

import com.example.testspringboot.converters.FilmMapper;
import com.example.testspringboot.dto.FilmDto;
import com.example.testspringboot.model.Acteur;
import com.example.testspringboot.exception.ActeurNotFoundException;
import com.example.testspringboot.exception.InvalidFilmException;
import com.example.testspringboot.exception.NullActeurException;
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

        if (film.getTitre() == null || film.getTitre().isEmpty()) {
            throw new InvalidFilmException("Le titre du film ne peut pas être nul ou vide!");
        }

        if (film.getDescription() == null || film.getDescription().isEmpty()) {
            throw new InvalidFilmException("La description du film ne peut pas être nulle ou vide!");
        }

        if (film.getActeurs() == null || film.getActeurs().isEmpty()) {
            throw new NullActeurException("Les acteurs ne peuvent pas être nuls ou vides!");
        }

        for (Acteur acteur : film.getActeurs()) {
            if (acteur.getNom() == null || acteur.getPrenom() == null) {
                throw new NullActeurException("Le nom et le prénom de l'acteur ne peuvent pas être nuls!");
            }
        }
        return filmMapper.modelToDto(filmRepository.save(filmMapper.dtoToModel(film)));
    }

    public List<FilmDto> getAllFilms(){
        return filmRepository.findAll().stream().map(filmMapper::modelToDto).collect(Collectors.toList());
    }

    public FilmDto getFilmById(Long id){
        //return filmMapper.modelToDto(filmRepository.findById(id).orElse(null));
        return filmMapper.modelToDto(filmRepository.findById(id)
                .orElseThrow(() -> new InvalidFilmException("Film not found with id " + id)));
    }
}
