package com.example.testspringboot.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ActeurDto {
    private Long id;
    private String nom;
    private String prenom;
}
