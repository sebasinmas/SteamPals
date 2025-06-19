package com.steampals.steampals.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;

import com.steampals.steampals.model.Usuario.Rol;

import java.util.Date;

@Component
public class JwtUtil {
    // Usa una clave secreta HS256 segura y suficientemente larga
    private static final String SECRET = "c2VjcmV0S2V5U2VjcmV0S2V5U2VjcmV0S2V5U2VjcmV0S2V5"; // Reemplaza con tu clave secreta
    // Asegúrate de que la clave sea de al menos 256 bits (32 bytes) para HS256
    private static final SecretKey KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));

    public String generarToken(String email, Rol rol) {
        return Jwts.builder()
                .subject(email)
                .claim("rol", rol)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000)) // 1 día
                .signWith(KEY)
                .compact();
    }

    public Claims validarToken(String token) {
        return Jwts.parser()
                .verifyWith(KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}