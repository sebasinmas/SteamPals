package com.steampals.steampals.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.steampals.steampals.model.Biblioteca;
@Repository
public interface BibliotecaRepository extends JpaRepository<Biblioteca, Long> {
    Optional<Biblioteca> findById(Long id);
    Optional<Biblioteca> findByUser(Long id);
    Optional<Biblioteca> findByJuego(Long id);
}
