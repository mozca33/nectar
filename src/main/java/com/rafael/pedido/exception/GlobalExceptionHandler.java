package com.rafael.pedido.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.rafael.pedido.dto.ErroDTO;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErroDTO> tratarExcecaoDeArgumentoIlegal(IllegalArgumentException ex) {
        return ResponseEntity
                .badRequest()
                .body(ErroDTO.fromException(ex, HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErroDTO> tratarExcecaoDeEstadoIlegal(IllegalStateException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErroDTO.fromException(ex, HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErroDTO> tratarExcecaoDeElementoNaoEncontrado(NoSuchElementException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErroDTO.fromException(ex, HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroDTO> tratarExcecaoDeValidacaoDeMetodo(MethodArgumentNotValidException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErroDTO.fromException(ex, HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErroDTO> tratarExcecaoDeRecursoNaoEncontrado(NoResourceFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErroDTO.fromException(ex, HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErroDTO> tratarExcecaoDeValidacaoDeHandler(HandlerMethodValidationException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErroDTO.fromException(ex, HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroDTO> tratarExcecaoGenerica(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErroDTO.fromException(ex, HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }
}
