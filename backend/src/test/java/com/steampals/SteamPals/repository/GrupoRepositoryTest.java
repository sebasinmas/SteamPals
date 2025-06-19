/*
package com.steampals.steampals.repository;

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
import com.steampals.steampals.model.MensajeGrupal;
import com.steampals.steampals.model.Usuario;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GrupoRepositoryTest {
    @Autowired
    private GrupoRepository grupoRepository;

    @BeforeEach // este método se ejecuta antes de cada prueba
    public void setup() {
        // Limpia base por si acaso
        grupoRepository.deleteAll();

        // Crea un grupo de prueba
        Grupo grupo = new Grupo();
        grupo.setId(0L);

        grupoRepository.save(grupo);
    }

    @Test
    @Order(1)
    void grupoDebeAgregarMiembro() {
        Optional<Grupo> resultado = grupoRepository.findById(0L);

        Usuario usuario = new Usuario();
        usuario.setNombreUsuario("Seba");

        resultado.get().agregarMiembro(usuario);
        assertTrue(resultado.get().getMiembros().contains(usuario));
    }

    @Test
    @Order(2)
    void grupoDebeEliminarMiembro() {
        Optional<Grupo> resultado = grupoRepository.findById(0L);
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario("Seba");

        resultado.get().agregarMiembro(usuario);
        resultado.get().eliminarMiembro(usuario);

        assertFalse(resultado.get().getMiembros().contains(usuario));
    }

    @Test
    @Order(3)
    void grupoDebeAgregarMensaje() {
        Optional<Grupo> resultado = grupoRepository.findById(0L);
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario("Seba");
        
        MensajeGrupal mensajeGrupo = new MensajeGrupal();

        resultado.get().enviarMensaje(mensajeGrupo);

        assertTrue(resultado.get().getMensajes().contains(mensajeGrupo));
    }

}*/
