package com.steampals.steampals.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import com.steampals.steampals.model.Grupo;



public interface GrupoRepository extends JpaRepository<Grupo, Long> {
    @NonNull
    Optional<Grupo> findById(Long id);
    Optional<Grupo> findByNombre(String nombre);
}