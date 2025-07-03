package com.steampals.steampals.repository;

import com.steampals.steampals.model.MatchUsuario;
import com.steampals.steampals.model.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MatchUsuarioRepositoryTest {

    @Autowired
    private MatchUsuarioRepository matchUsuarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario juan;
    private Usuario pedro;
    private Usuario maria;

    @BeforeEach
    void setUp() {
        juan = new Usuario();
        juan.setNombreUsuario("Juan");
        juan.setEmail("juan@test.com");
        juan.setContrasenia("1234");
        juan.setEdad(25);
        juan.setPais("Argentina");
        juan = usuarioRepository.save(juan);

        pedro = new Usuario();
        pedro.setNombreUsuario("Pedro");
        pedro.setEmail("pedro@test.com");
        pedro.setContrasenia("1234");
        pedro.setEdad(26);
        pedro.setPais("Chile");
        pedro = usuarioRepository.save(pedro);

        maria = new Usuario();
        maria.setNombreUsuario("Maria");
        maria.setEmail("maria@test.com");
        maria.setContrasenia("1234");
        maria.setEdad(27);
        maria.setPais("Uruguay");
        maria = usuarioRepository.save(maria);

        MatchUsuario matchJuanPedro = new MatchUsuario();
        matchJuanPedro.setUsuario1(juan);
        matchJuanPedro.setUsuario2(pedro);
        matchUsuarioRepository.save(matchJuanPedro);

        MatchUsuario matchPedroMaria = new MatchUsuario();
        matchPedroMaria.setUsuario1(pedro);
        matchPedroMaria.setUsuario2(maria);
        matchUsuarioRepository.save(matchPedroMaria);
    }

    @Test
    @DisplayName("findMatchEntreUsuarios debe encontrar match sin importar el orden de los usuarios")
    void debeEncontrarMatchEntreUsuariosSinImportarOrden() {
        Optional<MatchUsuario> match1 = matchUsuarioRepository.findMatchEntreUsuarios(juan, pedro);
        Optional<MatchUsuario> match2 = matchUsuarioRepository.findMatchEntreUsuarios(pedro, juan);

        assertThat(match1).isPresent();
        assertThat(match2).isPresent();
        assertThat(match1.get().getUsuario1()).isIn(juan, pedro);
        assertThat(match1.get().getUsuario2()).isIn(juan, pedro);
    }

    @Test
    @DisplayName("findAllMatchedUsers debe retornar todos los usuarios que hicieron match con el usuario dado")
    void debeRetornarTodosLosUsuariosQueHicieronMatch() {
        List<Usuario> matchesJuan = new ArrayList<>();
        matchesJuan.addAll(matchUsuarioRepository.findUsuariosDondeEsUsuario1(juan));
        matchesJuan.addAll(matchUsuarioRepository.findUsuariosDondeEsUsuario2(juan));

        List<Usuario> matchesPedro = new ArrayList<>();
        matchesPedro.addAll(matchUsuarioRepository.findUsuariosDondeEsUsuario1(pedro));
        matchesPedro.addAll(matchUsuarioRepository.findUsuariosDondeEsUsuario2(pedro));
        assertThat(matchesJuan).containsExactlyInAnyOrder(pedro);
        assertThat(matchesPedro).containsExactlyInAnyOrder(juan, maria);
    }

    @Test
    @DisplayName("findMatchBetween debe encontrar match sin importar el orden de los usuarios")
    void debeEncontrarMatchBetweenSinImportarOrden() {
        Optional<MatchUsuario> match = matchUsuarioRepository.findMatchBetween(pedro, maria);
        assertThat(match).isPresent();
        assertThat(match.get().getUsuario1()).isIn(pedro, maria);
        assertThat(match.get().getUsuario2()).isIn(pedro, maria);
    }

    @Test
    @DisplayName("findMatchEntreUsuarios retorna vacío si no existe match")
    void debeRetornarVacioSiNoExisteMatchEntreUsuarios() {
        Optional<MatchUsuario> match = matchUsuarioRepository.findMatchEntreUsuarios(juan, maria);
        assertThat(match).isNotPresent();
    }

    @Test
    void tablaMatchUsuarioExiste() {
        long count = matchUsuarioRepository.count();
        System.out.println("Cantidad de matches: " + count);
        assertThat(count).isGreaterThan(0);
    }
}