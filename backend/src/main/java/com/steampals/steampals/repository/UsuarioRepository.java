package com.steampals.SteamPals.model;

import org.springframework.data.jpa.repository.JpaRepository;
import com.steampals.steampals.model.Usuario;
import java.util.Optional;
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsuario(String usuario);
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findById(Long id);
}