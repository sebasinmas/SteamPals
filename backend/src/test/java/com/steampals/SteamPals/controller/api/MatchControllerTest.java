package com.steampals.steampals.controller.api;
import com.steampals.steampals.config.JwtUtil;
import com.steampals.steampals.model.Usuario;
import com.steampals.steampals.model.Usuario.Rol;
import com.steampals.steampals.repository.UsuarioRepository;
import com.steampals.steampals.service.MatchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;





@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class MatchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MatchService matchService;

    @MockitoBean
    private UsuarioRepository usuarioRepo;
    @Autowired
    private JwtUtil jwtUtil;

    private Usuario usuario;
    private Usuario objetivo;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombreUsuario("Usuario1");
        usuario.setEmail("usuario1@example.com");
        usuario.setRol(Rol.USUARIO);

        objetivo = new Usuario();
        objetivo.setId(2L);
        objetivo.setNombreUsuario("Usuario2");
        objetivo.setEmail("usuario2@example.com");
        objetivo.setRol(Rol.USUARIO);
        }

    @Test
    void testObtenerSugerencias() throws Exception {
        List<Usuario> sugerencias = Arrays.asList(objetivo);
        when(usuarioRepo.findById(1L)).thenReturn(Optional.of(usuario));
        when(matchService.obtenerPerfilesParaUsuario(usuario)).thenReturn(sugerencias);
        String jwt = "Bearer " + jwtUtil.generarToken(usuario.getEmail(), usuario.getRol()); // O un token hardcodeado
        mockMvc.perform(get("/api/match/sugerencias/1")
                        .header("Authorization", jwt))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(objetivo.getId()))
                .andExpect(jsonPath("$[0].nombreUsuario").value(objetivo.getNombreUsuario()));

        verify(usuarioRepo).findById(1L);
        verify(matchService).obtenerPerfilesParaUsuario(usuario);
    }

    @Test
    void testObtenerSugerenciasUsuarioNoEncontrado() throws Exception {
        when(usuarioRepo.findById(1L)).thenReturn(Optional.empty());
        String jwt = "Bearer " + jwtUtil.generarToken(usuario.getEmail(), usuario.getRol());
        mockMvc.perform(get("/api/match/sugerencias/1")
                        .header("Authorization", jwt))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testDarLike() throws Exception {
        when(usuarioRepo.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepo.findById(2L)).thenReturn(Optional.of(objetivo));
        String jwt = "Bearer " + jwtUtil.generarToken(usuario.getEmail(), usuario.getRol());
        mockMvc.perform(post("/api/match/like")
                        .param("actualId", "1")
                        .param("objetivoId", "2")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .header("Authorization", jwt))
                .andExpect(status().isOk());

        verify(usuarioRepo).findById(1L);
        verify(usuarioRepo).findById(2L);
        verify(matchService).darLike(usuario, objetivo);
    }

    @Test
    void testDarLikeUsuarioNoEncontrado() throws Exception {
        when(usuarioRepo.findById(1L)).thenReturn(Optional.empty());
        String jwt = "Bearer " + jwtUtil.generarToken(usuario.getEmail(), usuario.getRol());
        mockMvc.perform(post("/api/match/like")
                        .param("actualId", "1")
                        .param("objetivoId", "2")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .header("Authorization", jwt))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testDarLikeObjetivoNoEncontrado() throws Exception {
        when(usuarioRepo.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepo.findById(2L)).thenReturn(Optional.empty());
        String jwt = "Bearer " + jwtUtil.generarToken(usuario.getEmail(), usuario.getRol());
        mockMvc.perform(post("/api/match/like")
                        .param("actualId", "1")
                        .param("objetivoId", "2")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .header("Authorization", jwt))
                .andExpect(status().isInternalServerError());
    }
}