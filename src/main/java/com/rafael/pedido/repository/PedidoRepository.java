package com.rafael.pedido.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.rafael.pedido.model.Pedido;

@Repository
public class PedidoRepository {

    private final Map<String, Pedido> database = new HashMap<>();

    public void salvar(Pedido pedido) {
        database.put(pedido.getId(), pedido);
    }

    public Pedido consultar(String idPedido) {
        return database.get(idPedido);
    }

    public void remover(String idPedido) {
        database.remove(idPedido);
    }

    public boolean existePedidoPorId(String idPedido) {
        return database.containsKey(idPedido);
    }
}