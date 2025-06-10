package com.steampals.steampals.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.steampals.steampals.model.Grupo;
public interface GrupoRepository extends JpaRepository<Grupo, Long> {
    // @NonNull
    // Optional<Grupo> findById(@NonNull Long id);
    // Optional<Grupo> findByUser(Long id);
}