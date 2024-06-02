package com.example.testspringboot.dto;

import com.example.testspringboot.model.Acteur;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilmDto {
    private Long id;
    private String titre;
    private String description;
    private List<Acteur> acteurs;
}
