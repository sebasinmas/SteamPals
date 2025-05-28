package com.steampals.steampals.repository;


import com.steampals.steampals.model.Grupo;
import com.steampals.steampals.model.Usuario;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        grupo.setId(0);

        grupoRepository.save(grupo);

    }
    @Test
    @Order(1)
    void grupoDebeAgregarMiembro() {
        Optional<Grupo> resultado = grupoRepository.findById(0L);

        Usuario usuario = new Usuario();
        usuario.setUsuario("Seba");

        resultado.get().AgregarMiembro(usuario);
        assertTrue((resultado.get()).Find(usuario));
    }

    @Test
    @Order(2)
    void grupoDebeEliminarMiembro() {
        Optional<Grupo> resultado = grupoRepository.findById(0L);
        Usuario usuario = new Usuario();
        usuario.setUsuario("Seba");

        resultado.get().AgregarMiembro(usuario);
        resultado.get().eliminarMiembro(usuario);
        assertFalse(!resultado.get().miembros.contains(usuario));
    }
    void grupoDebeEliminarMiembro() {
        Optional<Grupo> resultado = grupoRepository.findById(0L);
        Usuario usuario = new Usuario();
        usuario.setUsuario("Seba");
        MensajeGrupo mensajeGrupo = new MensajeGrupo();
        mensajeGrupo.setTexto("Hola");
        resultado.get().AgregarMensajeGrupal(mensajeGrupo);
        assertTrue(resultado.get().mensajes.contains(mensajeGrupo));
    }

}
