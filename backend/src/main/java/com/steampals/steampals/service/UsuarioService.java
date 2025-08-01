package com.steampals.steampals.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.steampals.steampals.config.JwtUtil;
import com.steampals.steampals.dto.LoginDTO;
import com.steampals.steampals.dto.RegistroUsuarioDTO;
import com.steampals.steampals.dto.UsuarioDTO;
import com.steampals.steampals.dto.UsuarioUpdateDTO;
import com.steampals.steampals.model.Usuario;
import com.steampals.steampals.model.Usuario.Rol;
import com.steampals.steampals.repository.UsuarioRepository;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public Usuario actualizarUsuario(String email, UsuarioUpdateDTO dto) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setNombreUsuario(dto.getNombreUsuario());
        usuario.setDescripcion(dto.getDescripcion());
        usuario.setEdad(dto.getEdad());
        usuario.setPais(dto.getPais());

        return usuarioRepository.save(usuario);
    }

    public void eliminarUsuario(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuarioRepository.delete(usuario);
    }

    public Usuario registrarUsuario(RegistroUsuarioDTO dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("El email ya está en uso");
        }

        Usuario nuevoUsuario = Usuario.builder()
                .nombreUsuario(dto.getNombreUsuario())
                .email(dto.getEmail())
                .contrasenia(passwordEncoder.encode(dto.getContrasenia()))
                .edad(dto.getEdad())
                .pais(dto.getPais())
                .fechaDeRegistro(java.time.LocalDate.now())
                .rol(Rol.USUARIO)
                .build();
        return usuarioRepository.save(nuevoUsuario);
    }

    public String login(LoginDTO dto) {
        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));

        if (!passwordEncoder.matches(dto.getContrasenia(), usuario.getContrasenia())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        String token = jwtUtil.generarToken(usuario.getEmail(), usuario.getRol());
        return token;
    }

    public UsuarioDTO obtenerUsuarioPorEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("El email no puede ser nulo o vacío");
        }

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return UsuarioDTO.builder()
                .usuario(usuario.getNombreUsuario())
                .email(usuario.getEmail())
                .edad(usuario.getEdad())
                .pais(usuario.getPais())
                .descripcion(usuario.getDescripcion())
                .build();
    }

    //CASO DE USO VISUALIZAR OTRO USUARIO
    public UsuarioDTO obtenerPerfilPublico(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        UsuarioDTO dto = new UsuarioDTO();
        dto.setUsuario(usuario.getNombreUsuario());
        dto.setEmail(usuario.getEmail());
        dto.setEdad(usuario.getEdad());
        dto.setPais(usuario.getPais());
        dto.setDescripcion(usuario.getDescripcion());

        return dto;
    }
    
}
