package com.steampals.steampals.repository;

<<<<<<< HEAD
=======

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.steampals.steampals.model.Grupo;
>>>>>>> origin/ftr_luck
import com.steampals.steampals.model.MensajeGrupal;
import com.steampals.steampals.model.Usuario;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.steampals.steampals.model.Grupo;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GrupoRepositoryTest {
    @Autowired
    private GrupoRepository grupoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private MensajeGrupalRepository mensajeGrupalRepository;
    @Autowired
    private MatchUsuarioRepository matchUsuarioRepository;
    @BeforeEach // este método se ejecuta antes de cada prueba
    void setup() {
        // Limpia base por si acaso
        matchUsuarioRepository.deleteAll();
        mensajeGrupalRepository.deleteAll();
        usuarioRepository.deleteAll();
        grupoRepository.deleteAll();
        // Crea un grupo de prueba
        Grupo grupo = new Grupo();
        grupo.setNombre("Grupo 1");

        grupoRepository.save(grupo);
        // Crea un usuario de prueba
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario("Usuario 1");
        usuario.setEmail("prueba@correo.com");
        usuario.setContrasenia("contrasenia");
        usuario.setEdad(25);
        usuario.setPais("Argentina");
        usuarioRepository.save(usuario);
    }

     @Test
     @Order(1)
     void grupoDebeAgregarMiembro() {
         Optional<Grupo> grupo = grupoRepository.findByNombre("Grupo 1");
         assertTrue(grupo.isPresent(), "El grupo debe existir");
         Grupo grupoExistente = grupo.get();
         Optional<Usuario> usuario = usuarioRepository.findByEmail("prueba@correo.com");
         assertTrue(usuario.isPresent(), "El usuario debe existir");
         grupoExistente.getMiembros().add(usuario.get());
         grupoRepository.save(grupoExistente);
         assertTrue(grupoExistente.getMiembros().contains(usuario.get()), "El usuario debe ser miembro del grupo");
     }

     @Test
     @Order(2)
     void grupoDebeEliminarMiembro() {
            Optional<Grupo> grupo = grupoRepository.findByNombre("Grupo 1");
            assertTrue(grupo.isPresent(), "El grupo debe existir");
            Grupo grupoExistente = grupo.get();
            Optional<Usuario> usuario = usuarioRepository.findByEmail("prueba@correo.com");
            assertTrue(usuario.isPresent(), "El usuario debe existir");
            grupoExistente.getMiembros().remove(usuario.get());
            grupoRepository.save(grupoExistente);
            assertFalse(grupoExistente.getMiembros().contains(usuario.get()), "El usuario no debe ser miembro del grupo");
     }

     @Test
     @Order(3)
     void grupoDebeAgregarMensaje() {
         Optional<Grupo> grupoOpt = grupoRepository.findByNombre("Grupo 1");
         assertTrue(grupoOpt.isPresent(), "El grupo debe existir");

         Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail("prueba@correo.com");
         assertTrue(usuarioOpt.isPresent(), "El usuario debe existir");

         Grupo grupo = grupoOpt.get();
         Usuario usuario = usuarioOpt.get();

         MensajeGrupal mensaje = new MensajeGrupal();
         mensaje.setGrupo(grupo);
         mensaje.setEmisor(usuario);
         mensaje.setMensaje("Hola, este es un mensaje de prueba");

         // Actualiza ambos lados de la relación
         if (grupo.getMensajes() == null) {
             grupo.setMensajes(new ArrayList<>());
         }
         grupo.getMensajes().add(mensaje);

         mensajeGrupalRepository.save(mensaje);

         // Refrescamos el grupo desde la base de datos
         Grupo grupoActualizado = grupoRepository.findByIdConMensajes(grupo.getId()).orElseThrow(() -> new AssertionError("El grupo no fue encontrado"));

         // Aseguramos que tiene al menos un mensaje
         assertFalse(grupoActualizado.getMensajes() == null || grupoActualizado.getMensajes().isEmpty(), "El grupo debe tener al menos un mensaje");

         // Verificamos que el último mensaje es el que agregamos
         MensajeGrupal ultimoMensaje = null;
         if (grupoActualizado.getMensajes() instanceof java.util.List) {
             java.util.List<MensajeGrupal> mensajes = (java.util.List<MensajeGrupal>) grupoActualizado.getMensajes();
             ultimoMensaje = mensajes.get(mensajes.size() - 1);
         } else if (grupoActualizado.getMensajes() instanceof java.util.Set) {
             ultimoMensaje = grupoActualizado.getMensajes().stream().reduce((first, second) -> second).orElse(null);
         }
         assertNotNull(ultimoMensaje, "Debe haber al menos un mensaje");
         assertEquals("Hola, este es un mensaje de prueba", ultimoMensaje.getMensaje(), "El mensaje debe coincidir");
     }

}
<<<<<<< HEAD
=======

>>>>>>> origin/ftr_luck
