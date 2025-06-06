package com.steampals.steampals.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import com.steampals.steampals.model.Grupo;



import java.util.Optional;
public interface GrupoRepository extends JpaRepository<Grupo, Long> {
    // @NonNull
    // Optional<Grupo> findById(@NonNull Long id);
    // Optional<Grupo> findByUser(Long id);
}