package com.gestion.tasking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.gestion.tasking.model.AuthResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<AuthResponse> handleGeneralException(Exception ex) {
        return new ResponseEntity<>(
                new AuthResponse(500, "Ocurrió un error inesperado en el servidor: " + ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<AuthResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(new AuthResponse(400, "Datos inválidos: " + ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<AuthResponse> handleJsonParseException(HttpMessageNotReadableException ex) {
        return new ResponseEntity<>(
                new AuthResponse(400, "Hay errores en la validación, revisa que los datos en el JSON sean correctos."),
                HttpStatus.BAD_REQUEST);
    }

}
