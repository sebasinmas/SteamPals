package com.steampals.steampals.controller.api;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.MediaType;
import com.steampals.steampals.service.GrupoService;
import com.steampals.steampals.model.Grupo;
import com.steampals.steampals.model.Usuario;
import com.steampals.steampals.model.Usuario.Rol;
import com.steampals.steampals.config.JwtUtil;
import java.util.List;
import static org.mockito.Mockito.when;


@SpringBootTest
@AutoConfigureMockMvc

class GrupoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GrupoService grupoService;

    @Autowired
    private JwtUtil jwtUtil;

    private String getJwtToken() {
        // Simula un usuario de prueba
        Usuario usuario = new Usuario();
        usuario.setEmail("test@correo.com");
        usuario.setRol(Rol.USUARIO);
        // Puedes usar JwtUtil real o mockear el token
        return jwtUtil.generarToken(usuario.getEmail(), usuario.getRol());
    }

    @Test
    void testGetGruposDelUsuario() throws Exception {
        when(grupoService.obtenerGruposPorEmailUsuario("test@correo.com")).thenReturn(List.of());
        mockMvc.perform(get("/api/grupo")
                .header("Authorization", String.format("Bearer %s", getJwtToken())))
                .andExpect(status().isOk());
    }

    @Test
    void testCrearGrupo() throws Exception {
        Grupo grupo = new Grupo();
        grupo.setNombre("Grupo Test");
        when(grupoService.crearGrupo(org.mockito.ArgumentMatchers.any(Grupo.class), org.mockito.ArgumentMatchers.anyString())).thenReturn(grupo);
        mockMvc.perform(post("/api/grupo")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Grupo Test\"}")
                .header("Authorization", String.format("Bearer %s", getJwtToken())))
                .andExpect(status().isOk());
    }
}
