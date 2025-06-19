package com.steampals.steampals.service;

import com.steampals.steampals.model.MensajePrivado;
import com.steampals.steampals.model.Usuario;
import com.steampals.steampals.repository.MensajePrivadoRepository;
import com.steampals.steampals.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class MensajePrivadoServiceTest {

    @Autowired
    private MensajePrivadoRepository mensajeRepo;

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Test
    void testEnviarMensaje() {
        // Crear usuarios emisor y receptor
        Usuario emisor = new Usuario();
        emisor.setNombreUsuario("Sebastián");
        emisor = usuarioRepo.save(emisor);

        Usuario receptor = new Usuario();
        receptor.setNombreUsuario("Julio");
        receptor = usuarioRepo.save(receptor);

        // Crear mensaje
        MensajePrivado mensaje = new MensajePrivado();
        mensaje.setMensaje("Hola Julio");
        mensaje.setFechaEnvio(LocalDateTime.now());
        mensaje.setEmisor(emisor);
        mensaje.setReceptor(receptor);

        mensaje = mensajeRepo.save(mensaje);

        // Verificar
        assertThat(mensaje.getId()).isNotNull();
        assertThat(mensaje.getEmisor().getNombreUsuario()).isEqualTo("Sebastián");
    }
}
