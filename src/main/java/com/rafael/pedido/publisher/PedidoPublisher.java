package com.rafael.pedido.publisher;

import org.springframework.stereotype.Component;

import com.rafael.pedido.dto.PedidoDTO;
import com.rafael.pedido.model.Pedido;

@Component
public class PedidoPublisher {

    public PedidoDTO enviarPedido(Pedido pedido) {
        if (pedido.getCliente() != null && !pedido.getCliente().trim().isEmpty()) {
            return new PedidoDTO(pedido);
        } else {
            throw new IllegalArgumentException("O cliente informado é inválido.");
        }
    }
}