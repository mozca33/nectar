package com.rafael.pedido.exception;

import java.time.LocalDateTime;

/**
 * Representa a resposta de erro que será retornada para o cliente.
 * Contém informações sobre a mensagem de erro, status e horário do erro.
 */
public class ErrorResponse {
    private LocalDateTime horario;
    private String mensagem;
    private int status;

    /**
     * Construtor para inicializar a resposta de erro com a mensagem e status.
     * 
     * @param mensagem Mensagem de erro a ser retornada.
     * @param status   Código de status HTTP associado ao erro.
     */
    public ErrorResponse(String mensagem, int status) {
        this.horario = LocalDateTime.now();
        this.mensagem = mensagem;
        this.status = status;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    public String getMensagem() {
        return mensagem;
    }

    public int getStatus() {
        return status;
    }

    /**
     * Converte uma exceção em um objeto ErrorResponse com o status fornecido.
     * 
     * @param ex     A exceção a ser convertida.
     * @param status O código de status HTTP associado ao erro.
     * @return Um objeto ErrorResponse contendo a mensagem da exceção e o status.
     */
    public static ErrorResponse fromException(Exception ex, int status) {
        return new ErrorResponse(ex.getMessage(), status);
    }
}
