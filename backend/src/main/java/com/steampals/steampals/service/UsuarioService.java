package com.steampals.steampals.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.steampals.steampals.config.JwtUtil;
import com.steampals.steampals.dto.LoginDTO;
import com.steampals.steampals.dto.RegistroUsuarioDTO;
import com.steampals.steampals.dto.UsuarioDTO;
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

    public Usuario actualizarUsuario(RegistroUsuarioDTO dto) {
        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!usuario.getEmail().equals(dto.getEmail()) && usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("El email ya está en uso");
        }

        usuario.setNombreUsuario(dto.getNombreUsuario());
        usuario.setEmail(dto.getEmail());
        usuario.setContrasenia(passwordEncoder.encode(dto.getContrasenia()));
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
}
