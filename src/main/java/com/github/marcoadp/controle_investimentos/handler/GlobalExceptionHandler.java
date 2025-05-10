package com.github.marcoadp.controle_investimentos.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {NotFoundException.class, EntradaInvalidaException.class})
    public ResponseEntity<Object> handleNotFound(NotFoundException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", NOT_FOUND.value());
        body.put("erro", "NAO_ENCONTRADO");
        body.put("mensagem", ex.getMessage());

        return new ResponseEntity<>(body, NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleDefault(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", BAD_REQUEST.value());
        body.put("erro", "ERRO_DESCONHECIDO");
        body.put("mensagem", ex.getMessage());

        return new ResponseEntity<>(body, BAD_REQUEST);
    }
}
