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
    void darLike_matchExiste_seCompletaCorrectamente() {
        Usuario actual = new Usuario();
        actual.setId(1L);
        Usuario objetivo = new Usuario();
        objetivo.setId(2L);

        MatchUsuario matchExistente = new MatchUsuario();
        matchExistente.setUsuario1(objetivo);
        matchExistente.setUsuario2(actual);
        matchExistente.setUsuario1Like(true); // el objetivo ya dio like
        matchExistente.setUsuario2Like(false);

        when(matchUsuarioRepository.findMatchEntreUsuarios(actual, objetivo))
                .thenReturn(Optional.of(matchExistente));

        matchService.darLike(actual, objetivo);

        assertTrue(matchExistente.isUsuario1Like());
        assertTrue(matchExistente.isUsuario2Like());
        assertTrue(matchExistente.isCompleto());

        verify(matchUsuarioRepository).save(matchExistente);
    }

    @Test
    void darLike_noExisteMatch_seCreaNuevo() {
        Usuario actual = new Usuario();
        actual.setId(1L);
        Usuario objetivo = new Usuario();
        objetivo.setId(2L);

        when(matchUsuarioRepository.findMatchEntreUsuarios(actual, objetivo))
                .thenReturn(Optional.empty());

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
