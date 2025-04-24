package com.rafael.pedido.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.rafael.pedido.dto.PedidoDTO;
import com.rafael.pedido.model.Pedido;
import com.rafael.pedido.publisher.PedidoPublisher;
import com.rafael.pedido.repository.PedidoRepository;

/**
 * Classe de teste para o serviço de pedidos.
 * Esta classe contém testes unitários para verificar o funcionamento do
 * PedidoService.
 */
@SpringBootTest
class PedidoServiceTest {

    @Mock
    private PedidoRepository repository;

    @Mock
    private PedidoPublisher publisher;

    @InjectMocks
    private PedidoService service;

    /**
     * Testa o método criarPedido do PedidoService.
     * Verifica se o pedido é criado corretamente quando os dados são válidos.
     * Verifica se lança exceção quando o pedido já existe.
     */
    @Test
    void deveCriarPedido_quandoPedidoValido() {
        PedidoDTO pedidoDTO = new PedidoDTO("1", "Cliente Teste", 10.0);
        when(repository.existePorId("1")).thenReturn(false);

        Pedido pedido = service.criarPedido(pedidoDTO).toEntity();

        assertNotNull(pedido);
        assertEquals("1", pedido.getId());
        assertEquals("Cliente Teste", pedido.getCliente());
        assertEquals(10.0, pedido.getValorTotal());
        verify(repository).existePorId(pedido.getId());
        verify(repository).salvar(any());
        verify(publisher).enviarPedido(any());
    }

    /**
     * Testa o método criarPedido do PedidoService.
     * Verifica se lança exceção quando o pedido já existe.
     */
    @Test
    void deveLancarExcecao_quandoPedidoDuplicado() {
        PedidoDTO pedidoDTO = new PedidoDTO("1", "Cliente Teste", 150.0);
        when(repository.existePorId("1")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> service.criarPedido(pedidoDTO));
        verify(repository, never()).salvar(any());
        verify(publisher, never()).enviarPedido(any());
    }

    /**
     * Testa o método consultar do PedidoService.
     * Verifica se retorna o pedido corretamente quando existe.
     * Verifica se lança exceção quando o pedido não existe.
     */
    @Test
    void deveRetornarPedidoDTO_quandoConsultarPedidoExistente() {
        Pedido pedido = new Pedido("1", "Cliente Teste", 200.0);
        when(repository.consultar("1")).thenReturn(Optional.of(pedido));

        PedidoDTO result = service.consultar("1");

        assertNotNull(result);
        assertEquals("1", result.id());
        assertEquals("Cliente Teste", result.cliente());
        assertEquals(200.0, result.valorTotal());
    }

    /**
     * Testa o método consultar do PedidoService.
     * Verifica se lança exceção quando o pedido não existe.
     */
    @Test
    void deveRetornar400_QuandoConsultarPedidoInexistente() {
        when(repository.consultar("999")).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> service.consultar("999"));
    }
}
