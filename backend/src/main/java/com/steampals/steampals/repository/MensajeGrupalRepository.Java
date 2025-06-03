package com.steampals.steampals.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.steampals.steampals.model.MensajeGrupal;
@Repository
public interface MensajeGrupalRepository extends JpaRepository<MensajeGrupal, Long> {
    Optional<MensajeGrupal> findById(Long id);
}