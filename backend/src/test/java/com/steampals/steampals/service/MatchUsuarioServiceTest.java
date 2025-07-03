package com.steampals.steampals.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

import com.steampals.steampals.model.MatchUsuario;
import com.steampals.steampals.model.Usuario;
import com.steampals.steampals.repository.MatchUsuarioRepository;
import com.steampals.steampals.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class MatchUsuarioServiceTest {

    @Mock
    private MatchUsuarioRepository matchUsuarioRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private MatchService matchService;

    @Test
    void obtenerPerfilesParaUsuario_debeRetornarUsuariosNoVistos() {
        Usuario actual = new Usuario();
        actual.setId(1L);

        Usuario visto1 = new Usuario();
        visto1.setId(2L);

        Usuario visto2 = new Usuario();
        visto2.setId(5L);

        Usuario nuevo1 = new Usuario();
        nuevo1.setId(3L);
        Usuario nuevo2 = new Usuario();
        nuevo2.setId(4L);

        // Simula matches donde actual es usuario1 y usuario2
        when(matchUsuarioRepository.findUsuariosDondeEsUsuario1(actual)).thenReturn(List.of(visto1));
        when(matchUsuarioRepository.findUsuariosDondeEsUsuario2(actual)).thenReturn(List.of(visto2));
        when(usuarioRepository.findByIdNotIn(org.mockito.ArgumentMatchers.anyList()))
                .thenReturn(List.of(nuevo1, nuevo2));

        List<Usuario> resultado = matchService.obtenerPerfilesParaUsuario(actual);

        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(nuevo1));
        assertTrue(resultado.contains(nuevo2));
    }

    @Test
    void darLike_matchExiste_seCompletaCorrectamente() {
        Usuario actual = new Usuario();
        actual.setId(1L);

        Usuario objetivo = new Usuario();
        objetivo.setId(2L);

        MatchUsuario matchExistente = new MatchUsuario();
        matchExistente.setUsuario1(actual); // actual es usuario1
        matchExistente.setUsuario2(objetivo); // objetivo es usuario2
        matchExistente.setUsuario1Like(false);
        matchExistente.setUsuario2Like(true); // El otro ya dio like

        when(matchUsuarioRepository.findMatchBetween(
                ArgumentMatchers.any(Usuario.class),
                ArgumentMatchers.any(Usuario.class)))
                .thenReturn(Optional.of(matchExistente));

        matchService.darLike(actual, objetivo);

        assertTrue(matchExistente.isUsuario1Like());
        assertTrue(matchExistente.isUsuario2Like());
        assertTrue(matchExistente.isCompleto());

        verify(matchUsuarioRepository).save(matchExistente);
    }
}
