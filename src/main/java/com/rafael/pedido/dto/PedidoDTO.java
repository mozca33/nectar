package com.rafael.pedido.dto;

import com.rafael.pedido.model.Pedido;

public record PedidoDTO(String id, String cliente, double valorTotal) {

    public PedidoDTO(Pedido pedido) {
        this(pedido.getId(), pedido.getCliente(), pedido.getValorTotal());
    }

    public Pedido toEntity() {
        return new Pedido(id, cliente, valorTotal);
    }
}
