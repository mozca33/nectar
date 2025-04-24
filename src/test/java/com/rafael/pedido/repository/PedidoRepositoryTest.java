package com.rafael.pedido.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.rafael.pedido.model.Pedido;

/**
 * Classe de teste para o PedidoRepository.
 * Esta classe contém testes unitários para verificar o funcionamento do
 * PedidoRepository.
 */
public class PedidoRepositoryTest {
    private PedidoRepository repository = new PedidoRepository();

    @Test
    void deveSalvarEPersistirPedido() {
        Pedido pedido = new Pedido("1", "Cliente", 100.0);
        repository.salvar(pedido);

        assertTrue(repository.existePorId("1"));
        assertEquals(pedido, repository.consultar("1"));
    }

    /**
     * Testa o método existePorId do PedidoRepository.
     * Verifica se retorna false quando o pedido não existe.
     */
    @Test
    void deveRetornarNuloSePedidoNaoExiste() {
        assertNull(repository.consultar("nao-existe"));
    }
}