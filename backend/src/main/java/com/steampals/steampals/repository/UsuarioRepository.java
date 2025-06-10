package com.steampals.steampals.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import com.steampals.steampals.model.Usuario;
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByNombreUsuario(String usuario);
    Optional<Usuario> findByEmail(String email);
    @NonNull
    Optional<Usuario> findById(@NonNull Long id);
}