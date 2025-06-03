package com.steampals.steampals.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.steampals.steampals.model.Mensaje;
@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Long> {
    Optional<Mensaje> findById(Long id);
}