package com.steampals.steampals.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.steampals.steampals.model.MensajePrivado;
import com.steampals.steampals.model.Usuario;

@Repository
public interface MensajePrivadoRepository extends JpaRepository<MensajePrivado, Long> {
    List<MensajePrivado> findByEmisorOrReceptor(Usuario emisor, Usuario receptor);
}
