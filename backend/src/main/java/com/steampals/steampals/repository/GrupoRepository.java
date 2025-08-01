package com.steampals.steampals.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import com.steampals.steampals.model.Grupo;



public interface GrupoRepository extends JpaRepository<Grupo, Long> {
    @NonNull
    Optional<Grupo> findById(Long id);
    Optional<Grupo> findByNombre(String nombre);
    @Query("SELECT g FROM Grupo g LEFT JOIN FETCH g.mensajes WHERE g.id = :id")
    Optional<Grupo> findByIdConMensajes(@Param("id") Long id);

    // Buscar todos los grupos donde el usuario es miembro
    @Query("SELECT g FROM Grupo g JOIN g.miembros m WHERE m = :usuario")
    java.util.List<Grupo> findByMiembrosContaining(@Param("usuario") com.steampals.steampals.model.Usuario usuario);
}