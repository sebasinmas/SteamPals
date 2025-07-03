package com.steampals.steampals.repository;

import com.steampals.steampals.model.MensajeGrupal;
import com.steampals.steampals.model.Usuario;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.steampals.steampals.model.Grupo;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GrupoRepositoryTest {
    @Autowired
    private GrupoRepository grupoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private MensajeGrupalRepository mensajeGrupalRepository;
    @BeforeEach // este método se ejecuta antes de cada prueba
    void setup() {
        // Limpia base por si acaso
        grupoRepository.deleteAll();
        usuarioRepository.deleteAll();
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

         mensajeGrupalRepository.save(mensaje);

         // Refrescamos el grupo desde la base de datos
         Grupo grupoActualizado = grupoRepository.findByIdConMensajes(grupo.getId()).get();

         // Aseguramos que tiene al menos un mensaje
         assertFalse(grupoActualizado.getMensajes().isEmpty(), "El grupo debe tener al menos un mensaje");

         // Verificamos que el último mensaje es el que agregamos
         MensajeGrupal ultimoMensaje = grupoActualizado.getMensajes().get(grupoActualizado.getMensajes().size() - 1);
         assertEquals("Hola, este es un mensaje de prueba", ultimoMensaje.getMensaje(), "El mensaje debe coincidir");
     }

}
