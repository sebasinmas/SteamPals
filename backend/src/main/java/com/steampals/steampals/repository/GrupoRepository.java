package com.steampals.steampals.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.steampals.steampals.model.Grupo;

import lombok.NonNull;

public interface GrupoRepository extends JpaRepository<Grupo, Long> {
    @NonNull
    Optional<Grupo> findById(@NonNull Long id);
    Optional<Grupo> findByUser(Long id);
}