package com.steampals.steampals.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

import com.steampals.steampals.model.MatchUsuario;
import com.steampals.steampals.model.Usuario;
import com.steampals.steampals.repository.MatchUsuarioRepository;
import com.steampals.steampals.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class MatchServiceTest {

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

        Usuario visto = new Usuario();
        visto.setId(2L);

        Usuario nuevo1 = new Usuario();
        nuevo1.setId(3L);
        Usuario nuevo2 = new Usuario();
        nuevo2.setId(4L);

        when(matchUsuarioRepository.findAllMatchedUsers(actual)).thenReturn(List.of(visto));
        when(usuarioRepository.findByIdNotIn(List.of(1L, 2L))).thenReturn(List.of(nuevo1, nuevo2));

        List<Usuario> resultado = matchService.obtenerPerfilesParaUsuario(actual);

        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(nuevo1));
        assertTrue(resultado.contains(nuevo2));
    }

    @Test
    void darLike_matchExisteYSeCompleta() {
        Usuario actual = new Usuario();
        actual.setId(1L);
        Usuario objetivo = new Usuario();
        objetivo.setId(2L);

        MatchUsuario match = new MatchUsuario();
        match.setUsuario1(actual);
        match.setUsuario2(objetivo);
        match.setUsuario1Like(false);
        match.setUsuario2Like(true);

        when(matchUsuarioRepository.findMatchBetween(actual, objetivo)).thenReturn(Optional.of(match));

        matchService.darLike(actual, objetivo);

        assertTrue(match.isUsuario1Like());
        assertTrue(match.isUsuario2Like());
        assertTrue(match.isCompleto());
        verify(matchUsuarioRepository).save(match);
    }

    @Test
    void darLike_noExisteMatch_prepararNuevo() {
        Usuario actual = new Usuario();
        actual.setId(1L);
        Usuario objetivo = new Usuario();
        objetivo.setId(2L);

        when(matchUsuarioRepository.findMatchBetween(actual, objetivo)).thenReturn(Optional.empty());

        matchService.darLike(actual, objetivo);

        ArgumentCaptor<MatchUsuario> captor = ArgumentCaptor.forClass(MatchUsuario.class);
        verify(matchUsuarioRepository).save(captor.capture());

        MatchUsuario nuevo = captor.getValue();
        assertEquals(actual, nuevo.getUsuario1());
        assertEquals(objetivo, nuevo.getUsuario2());
        assertTrue(nuevo.isUsuario1Like());
        assertFalse(nuevo.isUsuario2Like());
        assertFalse(nuevo.isCompleto());
    }
}

