package com.example.testspringboot.controller;

import com.example.testspringboot.dto.FilmDto;
import com.example.testspringboot.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/film")
public class FilmController {
    @Autowired
    private FilmService filmService;

    @PostMapping
    public ResponseEntity<FilmDto> addFilm(@RequestBody FilmDto film) {
        return new ResponseEntity<>(filmService.saveFilm(film), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<FilmDto>> getAllFilms() {
        return new ResponseEntity<>(filmService.getAllFilms(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilmDto> getFilmById(@PathVariable Long id) {
        FilmDto film = filmService.getFilmById(id);
        if (film == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(film, HttpStatus.OK);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
