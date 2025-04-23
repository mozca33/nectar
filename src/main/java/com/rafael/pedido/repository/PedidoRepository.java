package com.rafael.pedido.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.rafael.pedido.model.Pedido;

@Repository
public class PedidoRepository {

    private final Map<String, Pedido> banco = new HashMap<>();

    public void salvar(Pedido pedido) {
        banco.put(pedido.getId(), pedido);
    }

    public Pedido consultar(String idPedido) {
        return banco.get(idPedido);
    }

    public boolean existePedidoPorId(String idPedido) {
        return banco.containsKey(idPedido);
    }
}