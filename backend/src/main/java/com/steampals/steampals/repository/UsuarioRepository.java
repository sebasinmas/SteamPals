package com.steampals.steampals.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.steampals.steampals.model.Usuario;
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByEmail(@NonNull String email);
    Optional<Usuario> findByEmail(@NonNull String email);
    Optional<Usuario> findByNombreUsuario(String usuario);
}

