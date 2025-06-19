package com.steampals.steampals.service;

import org.springframework.stereotype.Service;

import com.steampals.steampals.config.JwtUtil;

import java.util.logging.Logger;

import io.jsonwebtoken.Claims;


@Service
public class AuthService {
    private final JwtUtil jwtUtil;
    public AuthService(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    public boolean isTokenValido(String token) {
        try {
            Claims claims = jwtUtil.validarToken(token);
            if (claims == null || claims.getSubject() == null) {
                return false; // Token inválido o sin sujeto
            }
        
            if (claims.getExpiration() != null && claims.getExpiration().before(new java.util.Date())) {
                Logger.getLogger(AuthService.class.getName()).warning("Token expirado: " + token);
                return false; // Token expirado
            }

            Logger.getLogger(AuthService.class.getName()).info("Token válido: " + token);
            
            return true;
        } catch (Exception e) {
            // Si ocurre una excepción al validar el token, lo consideramos inválido
            return false;
        }
    }
}
