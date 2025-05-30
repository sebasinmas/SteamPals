package com.steampals.steampals.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.steampals.steampals.model.Grupo;
public interface GrupoRepository extends JpaRepository<Grupo, Long> {
    Optional<Grupo> findById(Long id);
    Optional<Grupo> findByUser(Long id);
}