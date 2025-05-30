package com.steampals.steampals.repository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.steampals.steampals.model.Usuario;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioRepositoryTest {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeEach // este método se ejecuta antes de cada prueba
    public void setup() {
         // Limpia base por si acaso
        usuarioRepository.deleteAll();

        // Crea un usuario de prueba
        Usuario usuario = new Usuario();
        usuario.setUsuario("Kevin");
        usuario.setEmail("kevin@email.com");

        usuarioRepository.save(usuario);

    }
    @Test
    @Order(1)
    void debeEncontrarUsuarioPorCorreo() {
        Optional<Usuario> resultado = usuarioRepository.findByEmail("kevin@email.com");

        assertTrue(resultado.isPresent());
        assertEquals("Kevin", resultado.get().getUsuario());
    }

    @Test
    @Order(2)
    void noDebeEncontrarUsuarioInexistente() {
        Optional<Usuario> resultado = usuarioRepository.findByEmail("otro@email.com");

        assertFalse(resultado.isPresent());
    }
    
}
