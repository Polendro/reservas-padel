package com.pgolmeda.padelbooking.infrastructure.adapter.out.security;

import com.pgolmeda.padelbooking.application.port.out.TokenProvider;
import com.pgolmeda.padelbooking.domain.model.Usuario;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtTokenProviderAdapter implements TokenProvider {

    private final SecretKey clave;
    private final long expiracionMinutos;

    public JwtTokenProviderAdapter(@Value("${app.jwt.secret}") String secreto,
                                    @Value("${app.jwt.expiracion-minutos}") long expiracionMinutos) {
        this.clave = Keys.hmacShaKeyFor(secreto.getBytes(StandardCharsets.UTF_8));
        this.expiracionMinutos = expiracionMinutos;
    }

    @Override
    public String generar(Usuario usuario) {
        Instant ahora = Instant.now();
        return Jwts.builder()
                .subject(usuario.getEmail())
                .issuedAt(Date.from(ahora))
                .expiration(Date.from(ahora.plus(expiracionMinutos, ChronoUnit.MINUTES)))
                .signWith(clave)
                .compact();
    }

    @Override
    public Optional<String> validarYObtenerEmail(String token) {
        try {
            String email = Jwts.parser()
                    .verifyWith(clave)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
            return Optional.of(email);
        } catch (JwtException | IllegalArgumentException e) {
            // Firma inválida, token expirado, formato roto... cualquier fallo se trata
            // igual: el token no es válido, punto. No hace falta distinguir el motivo.
            return Optional.empty();
        }
    }
}
