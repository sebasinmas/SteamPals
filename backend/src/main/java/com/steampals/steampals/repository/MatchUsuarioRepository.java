package com.steampals.steampals.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.steampals.steampals.model.MatchUsuario;
import com.steampals.steampals.model.Usuario;

@Repository
public interface MatchUsuarioRepository extends JpaRepository<MatchUsuario, Long> {
        @Query("SELECT m FROM MatchUsuario m WHERE " +
                        "(m.usuario1 = :usuarioA AND m.usuario2 = :usuarioB) " +
                        "OR (m.usuario1 = :usuarioB AND m.usuario2 = :usuarioA)")
        Optional<MatchUsuario> findMatchEntreUsuarios(
                        @Param("usuarioA") Usuario usuarioA,
                        @Param("usuarioB") Usuario usuarioB);

        @Query("SELECT CASE " +
                        "WHEN m.usuario1 = :usuario THEN m.usuario2 " +
                        "ELSE m.usuario1 END " +
                        "FROM MatchUsuario m " +
                        "WHERE m.usuario1 = :usuario OR m.usuario2 = :usuario")
        List<Usuario> findAllMatchedUsers(@Param("usuario") Usuario usuario);

        @Query("SELECT m FROM MatchUsuario m WHERE " +
                        "(m.usuario1 = :m1 AND m.usuario2 = :m2) " +
                        "OR (m.usuario1 = :m2 AND m.usuario2 = :m1)")
        Optional<MatchUsuario> findMatchBetween(Usuario m1, Usuario m2);

        // tests
        @Query("SELECT m.usuario2 FROM MatchUsuario m WHERE m.usuario1 = :usuario")
        List<Usuario> findUsuariosDondeEsUsuario1(@Param("usuario") Usuario usuario);

        @Query("SELECT m.usuario1 FROM MatchUsuario m WHERE m.usuario2 = :usuario")
        List<Usuario> findUsuariosDondeEsUsuario2(@Param("usuario") Usuario usuario);

}
