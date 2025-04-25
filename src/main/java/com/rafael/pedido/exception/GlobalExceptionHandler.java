package com.rafael.pedido.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.NoSuchElementException;

/**
 * Classe responsável por tratar as exceções lançadas na aplicação de forma
 * global.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Método que trata exceções de argumento ilegal.
     *
     * @param ex a exceção lançada
     * @return Resposta com o status 400 e a mensagem de erro
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> tratarExcecaoDeArgumentoIlegal(IllegalArgumentException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.fromException(ex, HttpStatus.BAD_REQUEST.value()));
    }

    /**
     * Método que trata exceções de estado ilegal.
     *
     * @param ex a exceção lançada
     * @return Resposta com o status 400 e a mensagem de erro
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> tratarExcecaoDeEstadoIlegal(IllegalStateException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.fromException(ex, HttpStatus.BAD_REQUEST.value()));
    }

    /**
     * Método que trata exceções de validação de método.
     *
     * @param ex a exceção lançada
     * @return Resposta com o status 422 e a mensagem de erro
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> tratarExcecaoDeValidacaoDeMetodo(MethodArgumentNotValidException ex) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ErrorResponse.fromException(ex, HttpStatus.UNPROCESSABLE_ENTITY.value()));
    }

    /**
     * Método que trata exceções de validação de handler.
     *
     * @param ex a exceção lançada
     * @return Resposta com o status 400 e a mensagem de erro
     */
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorResponse> tratarExcecaoDeValidacaoDeHandler(HandlerMethodValidationException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.fromException(ex, HttpStatus.BAD_REQUEST.value()));
    }

    /**
     * Método que trata exceções de elemento não encontrado.
     *
     * @param ex a exceção lançada
     * @return Resposta com o status 404 e a mensagem de erro
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> tratarExcecaoDeElementoNaoEncontrado(NoSuchElementException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.fromException(ex, HttpStatus.NOT_FOUND.value()));
    }

    /**
     * Método que trata exceções de recurso não encontrado.
     *
     * @param ex a exceção lançada
     * @return Resposta com o status 404 e a mensagem de erro
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> tratarExcecaoDeRecursoNaoEncontrado(NoResourceFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.fromException(ex, HttpStatus.NOT_FOUND.value()));
    }

    /**
     * Método que trata exceções genéricas.
     *
     * @param ex a exceção lançada
     * @return Resposta com o status 500 e a mensagem de erro
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> tratarExcecaoGenerica(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.fromException(ex, HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }
}
