package com.steampals.steampals.service;
/* 
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.steampals.steampals.model.Grupo;
import com.steampals.steampals.model.MensajeGrupal;
import com.steampals.steampals.model.Usuario;
import com.steampals.steampals.repository.GrupoRepository;
import com.steampals.steampals.repository.MensajeGrupalRepository;
import com.steampals.steampals.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class MensajeGrupalServiceTest {

    @InjectMocks
    private MensajeGrupalService mensajeGrupalService;

    @Mock
    private MensajeGrupalRepository mensajeGrupalRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private GrupoRepository grupoRepository;

    @Test
    void enviarMensaje_debeGuardarYRetornarMensajeGrupal() {
        Long grupoId = 1L;
        Long emisorId = 2L;
        String texto = "test";

        Usuario emisor = new Usuario();
        emisor.setId(emisorId);

        Grupo grupo = new Grupo();
        grupo.setId(grupoId);

        MensajeGrupal mensajeEsperado = new MensajeGrupal();
        mensajeEsperado.setId(10L);
        mensajeEsperado.setMensaje(texto);
        mensajeEsperado.setEmisor(emisor);
        mensajeEsperado.setGrupo(grupo);
        mensajeEsperado.setFechaEnvio(LocalDateTime.now());

        when(usuarioRepository.findById(emisorId)).thenReturn(Optional.of(emisor));
        when(grupoRepository.findById(grupoId)).thenReturn(Optional.of(grupo));
        when(mensajeGrupalRepository.save(any(MensajeGrupal.class))).thenReturn(mensajeEsperado);

        MensajeGrupal resultado;
        resultado = mensajeGrupalService.enviarMensaje(grupoId, emisorId, texto);
        assertEquals(texto, resultado.getMensaje());
        assertEquals(grupoId, resultado.getGrupo().getId());
        assertEquals(emisorId, resultado.getEmisor().getId());
        assertNotNull(resultado.getFechaEnvio());
        verify(mensajeGrupalRepository).save(any(MensajeGrupal.class));
    }
}
    */
