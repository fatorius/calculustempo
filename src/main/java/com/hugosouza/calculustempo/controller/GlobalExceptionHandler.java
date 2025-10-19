package com.hugosouza.calculustempo.controller;

import com.hugosouza.calculustempo.interfaces.ErrorResponse;
import com.hugosouza.calculustempo.interfaces.ResponseData;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseData<?> handleBadCredentials(BadCredentialsException ex) {
        return new ErrorResponse<>("Usuário ou senha inválidos.");
    }

    @ExceptionHandler(Exception.class)
    public ResponseData<?> handleGeneric(Exception ex) {
        return new ErrorResponse<>("Erro interno no servidor.");
    }
}
