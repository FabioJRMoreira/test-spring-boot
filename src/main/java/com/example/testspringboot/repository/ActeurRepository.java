package com.example.testspringboot.repository;

import com.example.testspringboot.model.Acteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActeurRepository extends JpaRepository<Acteur,Long> {
}
