package com.rafael.pedido.dto;

import java.time.LocalDateTime;

public class ErroDTO {
    private LocalDateTime horario;
    private String mensagem;
    private int status;

    public ErroDTO(String mensagem, int status) {
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

    public static ErroDTO fromException(Exception ex, int status) {
        return new ErroDTO(ex.getMessage(), status);
    }
}
