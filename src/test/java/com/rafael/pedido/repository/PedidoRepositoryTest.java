package com.rafael.pedido.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.rafael.pedido.model.Pedido;

public class PedidoRepositoryTest {
    private PedidoRepository repository = new PedidoRepository();

    @Test
    void deveSalvarEPersistirPedido() {
        Pedido pedido = new Pedido("1", "Cliente", 100.0);
        repository.salvar(pedido);

        assertTrue(repository.existePedidoPorId("1"));
        assertEquals(pedido, repository.consultar("1"));
    }

    @Test
    void deveRetornarNuloSePedidoNaoExiste() {
        assertNull(repository.consultar("nao-existe"));
    }
}