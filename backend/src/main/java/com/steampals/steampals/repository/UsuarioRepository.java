package com.steampals.steampals.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.steampals.steampals.model.Usuario;
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsuario(String usuario);
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findById(Long id);
}