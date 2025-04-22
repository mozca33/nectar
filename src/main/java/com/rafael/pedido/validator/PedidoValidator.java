package com.rafael.pedido.validator;

import org.springframework.stereotype.Component;

import com.rafael.pedido.model.Pedido;

@Component
public class PedidoValidator {
    public void validarPedido(Pedido pedido) {
        if (pedido == null) {
            throw new IllegalArgumentException("O Pedido não pode ser nulo.");
        }

        if (pedido.getId() == null || pedido.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("O Id deve ser preenchido");
        }

        if (pedido.getCliente() == null || pedido.getCliente().trim().isEmpty()) {
            throw new IllegalArgumentException("O Cliente deve ser preenchido.");
        }

        if (pedido.getValorTotal() <= 0 || Double.isNaN(pedido.getValorTotal())) {
            throw new IllegalArgumentException("O Valor total deve ser maior que zero.");
        }
    }
}
