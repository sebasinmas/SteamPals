package com.steampals.steampals.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.steampals.steampals.model.MensajePrivado;
import com.steampals.steampals.model.Usuario;

import lombok.NonNull;

public interface MensajePrivadoRepository extends JpaRepository<MensajePrivado, Long> {
    List<MensajePrivado> findByEmisorOrReceptor(Usuario emisor, Usuario receptor);
}
