package com.steampals.steampals.repository;

import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.steampals.steampals.model.Juego;

import java.util.Optional;

@Repository
public interface JuegoRepository extends JpaRepository<Juego, Long> {
    @NonNull
    Optional<Juego> findById(@NonNull Long id);
    
}