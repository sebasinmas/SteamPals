package com.steampals.steampals.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.steampals.steampals.dto.EliminarUsuarioDTO;
import com.steampals.steampals.dto.LoginDTO;
import com.steampals.steampals.model.Usuario;
import com.steampals.steampals.model.Usuario.Rol;
import com.steampals.steampals.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        usuarioRepository.deleteAll(); // <--- limpia todo
        Usuario admin = Usuario.builder()
                .nombreUsuario("AdminTest")
                .email("admin@test.com")
                .contrasenia(passwordEncoder.encode("admin123"))
                .edad(30)
                .pais("Chile")
                .rol(Rol.ADMINISTRADOR)
                .build();

        usuarioRepository.save(admin);
    }

    private String obtenerToken(String email, String password) throws Exception {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail(email);
        loginDTO.setContrasenia(password);

        String respuesta = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return respuesta; // El token JWT como string
    }

    @Test
    void eliminarUsuario_conTokenAdmin_retornaNoContent() throws Exception {
        // Arrange
        Usuario victima = Usuario.builder()
                .nombreUsuario("Victima")
                .email("victima@test.com")
                .contrasenia(passwordEncoder.encode("1234"))
                .edad(22)
                .pais("Chile")
                .rol(Rol.USUARIO)
                .build();
        usuarioRepository.save(victima);

        String token = obtenerToken("admin@test.com", "admin123");

        EliminarUsuarioDTO dto = new EliminarUsuarioDTO();
        dto.setEmail("victima@test.com");

        // Act & Assert
        mockMvc.perform(delete("/api/usuario")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNoContent());
    }

    @Test
    void crearUsuario_conDatosValidos_retornaCreated() throws Exception {
        // Arrange
        var registroUsuarioDTO = new com.steampals.steampals.dto.RegistroUsuarioDTO();
        registroUsuarioDTO.setNombreUsuario("NuevoUsuario");
        registroUsuarioDTO.setEmail("nuevo@usuario.com");
        registroUsuarioDTO.setContrasenia("password123");
        registroUsuarioDTO.setEdad(25);
        registroUsuarioDTO.setPais("Argentina");

        // Act & Assert
        mockMvc.perform(post("/api/usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registroUsuarioDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Usuario creado con ID:")));
    }

    @Test
    void crearUsuario_conEmailExistente_retornaBadRequest() throws Exception {
        // Arrange
        var registroUsuarioDTO = new com.steampals.steampals.dto.RegistroUsuarioDTO();
        registroUsuarioDTO.setNombreUsuario("AdminTest");
        registroUsuarioDTO.setEmail("admin@test.com"); // ya existe
        registroUsuarioDTO.setContrasenia("admin123");
        registroUsuarioDTO.setEdad(30);
        registroUsuarioDTO.setPais("Chile");

        // Act & Assert
        mockMvc.perform(post("/api/usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registroUsuarioDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void actualizarUsuario_conDatosValidos_retornaOk() throws Exception {
        // Arrange
        var registroUsuarioDTO = new com.steampals.steampals.dto.UsuarioUpdateDTO();
        registroUsuarioDTO.setNombreUsuario("AdminTestActualizado");
        registroUsuarioDTO.setEdad(31);
        registroUsuarioDTO.setPais("Argentina");

        // Act & Assert
        mockMvc.perform(put("/api/usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registroUsuarioDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreUsuario").value("AdminTestActualizado"))
                .andExpect(jsonPath("$.edad").value(31))
                .andExpect(jsonPath("$.pais").value("Argentina"));
    }

    @Test
    void actualizarUsuario_conEmailInexistente_retornaBadRequest() throws Exception {
        // Arrange
        var registroUsuarioDTO = new com.steampals.steampals.dto.UsuarioUpdateDTO();
        registroUsuarioDTO.setNombreUsuario("NoExiste");
        registroUsuarioDTO.setEdad(20);
        registroUsuarioDTO.setPais("Peru");

        // Act & Assert
        mockMvc.perform(put("/api/usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registroUsuarioDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getPerfilUsuario_conTokenValido_retornaUsuarioDTO() throws Exception {
        // Arrange
        String token = obtenerToken("admin@test.com", "admin123");

        // Act & Assert
        mockMvc.perform(get("/api/usuario/me")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("admin@test.com"))
                .andExpect(jsonPath("$.nombreUsuario").value("AdminTest"));
    }

    @Test
    void eliminarUsuario_sinToken_retornaForbidden() throws Exception {
        EliminarUsuarioDTO dto = new EliminarUsuarioDTO();
        dto.setEmail("admin@test.com");

        mockMvc.perform(delete("/api/usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isForbidden());
    }

    @Test
    void eliminarUsuario_conTokenNoAdmin_retornaForbidden() throws Exception {
        // Arrange
        Usuario user = Usuario.builder()
                .nombreUsuario("UsuarioNormal")
                .email("user@test.com")
                .contrasenia(passwordEncoder.encode("user123"))
                .edad(20)
                .pais("Chile")
                .rol(Rol.USUARIO)
                .build();
        usuarioRepository.save(user);

        String token = obtenerToken("user@test.com", "user123");

        EliminarUsuarioDTO dto = new EliminarUsuarioDTO();
        dto.setEmail("admin@test.com");

        // Act & Assert
        mockMvc.perform(delete("/api/usuario")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isForbidden());
    }
}
