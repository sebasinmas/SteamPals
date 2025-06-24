package com.steampals.steampals.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.steampals.steampals.model.UsuarioTieneJuego;
@Repository
public interface UsuarioTieneJuegoRepository extends JpaRepository<UsuarioTieneJuego, Long> {
    Optional<UsuarioTieneJuego> findById(Long id);
}