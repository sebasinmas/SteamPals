package com.steampals.steampals.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.steampals.steampals.model.Grupo;

import java.util.Optional;
public interface GrupoRepository extends JpaRepository<Grupo, Long> {
    Optional<Grupo> findById(Long id);
    Optional<Grupo> findByUser(Long id);
}