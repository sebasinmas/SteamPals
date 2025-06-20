package com.steampals.steampals.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.steampals.steampals.model.MensajeGrupal;

@Repository
public interface MensajeGrupalRepository extends JpaRepository<MensajeGrupal, Long> {
    List<MensajeGrupal> findByGrupoIdOrderByFechaEnvioAsc(Long grupoId);
}