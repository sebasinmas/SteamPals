package com.steampals.SteamPals.model;

import org.springframework.data.jpa.repository.JpaRepository;
import com.steampals.SteamPals.model.Usuario;
import java.util.Optional;
public interface GrupoRepository extends JpaRepository<Grupo, Long> {
    Optional<Grupo> findById(Long id);
    Optional<Grupo> findByUser(Long id);
}