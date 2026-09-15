package com.pgolmeda.padelbooking.infrastructure.adapter.out.security;

import com.pgolmeda.padelbooking.domain.model.Usuario;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Test sin mocks: es una clase con lógica de verdad (firmar y verificar), así que
// merece la pena probarla directamente en vez de solo confiar en que la librería funciona.
class JwtTokenProviderAdapterTest {

    private static final String SECRETO = "un-secreto-de-test-que-no-es-el-de-verdad-1234567890";

    @Test
    void un_token_generado_se_valida_y_devuelve_el_email_correcto() {
        JwtTokenProviderAdapter provider = new JwtTokenProviderAdapter(SECRETO, 60);
        Usuario usuario = Usuario.nuevo("pablo@test.com", "hash-cualquiera");

        String token = provider.generar(usuario);
        Optional<String> email = provider.validarYObtenerEmail(token);

        assertEquals(Optional.of("pablo@test.com"), email);
    }

    @Test
    void un_token_con_texto_basura_no_es_valido() {
        JwtTokenProviderAdapter provider = new JwtTokenProviderAdapter(SECRETO, 60);

        assertTrue(provider.validarYObtenerEmail("esto-no-es-un-jwt").isEmpty());
    }

    @Test
    void un_token_firmado_con_otro_secreto_no_es_valido() {
        JwtTokenProviderAdapter emisor = new JwtTokenProviderAdapter(SECRETO, 60);
        JwtTokenProviderAdapter verificador = new JwtTokenProviderAdapter("otro-secreto-completamente-distinto-1234567890", 60);
        Usuario usuario = Usuario.nuevo("pablo@test.com", "hash-cualquiera");

        String token = emisor.generar(usuario);

        assertTrue(verificador.validarYObtenerEmail(token).isEmpty());
    }

    @Test
    void un_token_ya_expirado_no_es_valido() {
        // -1 minuto de expiración: el token nace ya caducado.
        JwtTokenProviderAdapter provider = new JwtTokenProviderAdapter(SECRETO, -1);
        Usuario usuario = Usuario.nuevo("pablo@test.com", "hash-cualquiera");

        String token = provider.generar(usuario);

        assertTrue(provider.validarYObtenerEmail(token).isEmpty());
    }
}
