package com.steampals.steampals.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.steampals.steampals.model.MensajeGrupal;

public interface MensajeGrupalRepository extends JpaRepository<MensajeGrupal, Long> {
    List<MensajeGrupal> findByGrupoIdOrderByFechaEnvioAsc(Long grupoId);
}