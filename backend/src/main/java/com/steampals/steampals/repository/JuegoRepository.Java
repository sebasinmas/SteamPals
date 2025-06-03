package com.steampals.steampals.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.steampals.steampals.model.Juego;
@Repository
public interface JuegoRepository extends JpaRepository<Juego, Long> {
    Optional<Juego> findById(Long id);
}