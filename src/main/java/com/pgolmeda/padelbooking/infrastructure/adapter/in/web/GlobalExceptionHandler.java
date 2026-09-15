package com.pgolmeda.padelbooking.infrastructure.adapter.in.web;

import com.pgolmeda.padelbooking.domain.exception.CancelacionFueraDePlazoException;
import com.pgolmeda.padelbooking.domain.exception.CredencialesInvalidasException;
import com.pgolmeda.padelbooking.domain.exception.EmailYaRegistradoException;
import com.pgolmeda.padelbooking.domain.exception.PistaNoEncontradaException;
import com.pgolmeda.padelbooking.domain.exception.ReservaNoEncontradaException;
import com.pgolmeda.padelbooking.domain.exception.ReservaSolapadaException;
import com.pgolmeda.padelbooking.domain.exception.ReservaYaCanceladaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

// Centraliza el mapeo de excepciones de dominio a códigos HTTP, para no repetir
// try/catch en cada controller.
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PistaNoEncontradaException.class)
    public ResponseEntity<Map<String, Object>> pistaNoEncontrada(PistaNoEncontradaException ex) {
        return error(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ReservaSolapadaException.class)
    public ResponseEntity<Map<String, Object>> reservaSolapada(ReservaSolapadaException ex) {
        return error(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(ReservaNoEncontradaException.class)
    public ResponseEntity<Map<String, Object>> reservaNoEncontrada(ReservaNoEncontradaException ex) {
        return error(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler({ReservaYaCanceladaException.class, CancelacionFueraDePlazoException.class})
    public ResponseEntity<Map<String, Object>> cancelacionInvalida(RuntimeException ex) {
        return error(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(EmailYaRegistradoException.class)
    public ResponseEntity<Map<String, Object>> emailYaRegistrado(EmailYaRegistradoException ex) {
        return error(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(CredencialesInvalidasException.class)
    public ResponseEntity<Map<String, Object>> credencialesInvalidas(CredencialesInvalidasException ex) {
        return error(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> validacionFallida(MethodArgumentNotValidException ex) {
        String detalle = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return error(HttpStatus.BAD_REQUEST, detalle);
    }

    private ResponseEntity<Map<String, Object>> error(HttpStatus status, String mensaje) {
        return ResponseEntity.status(status).body(Map.of(
                "timestamp", Instant.now().toString(),
                "status", status.value(),
                "mensaje", mensaje));
    }
}
