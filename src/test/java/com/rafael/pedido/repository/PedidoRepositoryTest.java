package com.rafael.pedido.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.rafael.pedido.model.Pedido;

/**
 * Classe de teste para o PedidoRepository.
 * Esta classe contém testes unitários para verificar o funcionamento do
 * PedidoRepository.
 */
public class PedidoRepositoryTest {
    private PedidoRepository repository = new PedidoRepository();

    /**
     * Testa o método salvar do PedidoRepository.
     * Verifica se o pedido é salvo corretamente no repositório.
     */
    @Test
    void deveSalvar_quandoPedidoValido() {
        Pedido pedido = new Pedido("123", "Cliente X", 10000.0);
        repository.salvar(pedido);
        Optional<Pedido> pedidoSalvo = repository.consultar("123");

        assertTrue(pedidoSalvo.isPresent());
        assertEquals("123", pedidoSalvo.get().getId());
        assertEquals("Cliente X", pedidoSalvo.get().getCliente());
        assertEquals(10000.0, pedidoSalvo.get().getValorTotal());
    }

    /**
     * Testa o método consultar do PedidoRepository.
     * Verifica se o pedido é retornado corretamente quando existe.
     * Verifica se retorna vazio quando o pedido não existe.
     */
    @Test
    void deveRetornarPedido_quandoPedidoExistente() {
        Pedido pedido = new Pedido("1", "Cliente Y", 200.0);
        repository.salvar(pedido);

        Optional<Pedido> resultado = repository.consultar("1");

        assertTrue(resultado.isPresent());
    }

    /**
     * Testa o método consultar do PedidoRepository.
     * Verifica se retorna vazio quando o pedido não existe.
     */
    @Test
    void deveRetornarVazio_quandoPedidoNaoExistente() {
        Optional<Pedido> resultado = repository.consultar("999");

        assertFalse(resultado.isPresent());
    }

    /**
     * Testa o método existePorId do PedidoRepository.
     * Verifica se retorna true quando o pedido existe e false quando não existe.
     */
    @Test
    void deveRetornarTrue_quandoPedidoExistePorId() {
        Pedido pedido = new Pedido("456", "Cliente Z", 350.0232);
        repository.salvar(pedido);

        assertTrue(repository.existePorId("456"));
    }

    /**
     * Testa o método existePorId do PedidoRepository.
     * Verifica se retorna false quando o pedido não existe.
     */
    @Test
    void deveRetornarFalse_quandoPedidoNaoExistePorId() {
        assertFalse(repository.existePorId("000"));
    }
}