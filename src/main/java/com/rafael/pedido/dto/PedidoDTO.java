package com.rafael.pedido.dto;

import com.rafael.pedido.model.Pedido;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

/**
 * DTO (Data Transfer Object) usado para transportar os dados de um pedido.
 * Inclui validações para garantir que os dados sejam válidos.
 */
public record PedidoDTO(
        @NotBlank(message = "ID do pedido não pode ser nulo ou vazio.") String id,
        @NotBlank(message = "Cliente não pode ser nulo ou vazio.") String cliente,
        @Positive(message = "Valor total deve ser positivo.") double valorTotal) {

    /**
     * Construtor que converte um objeto Pedido para um PedidoDTO.
     * 
     * @param pedido O objeto Pedido a ser convertido.
     */
    public PedidoDTO(Pedido pedido) {
        this(pedido.getId(), pedido.getCliente(), pedido.getValorTotal());
    }

    /**
     * Converte o PedidoDTO de volta para a entidade Pedido.
     * 
     * @return Um objeto Pedido com os dados convertidos do DTO.
     */
    public Pedido toEntity() {
        return new Pedido(id, cliente, valorTotal);
    }
}
