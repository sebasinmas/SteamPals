package com.steampals.SteamPals.repository;

import com.steampals.SteamPals.model.Grupo;
import com.steampals.SteamPals.model.GrupoRepository;
import com.steampals.SteamPals.model.Usuario;
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
        GrupoRepository.deleteAll();

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
        assertTrue(resultado.get().find(usuario));
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

}
