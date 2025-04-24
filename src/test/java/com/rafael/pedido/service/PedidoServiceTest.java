package com.rafael.pedido.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.MethodArgumentNotValidException;

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
    void deveCriarPedidoComSucesso() {
        Pedido pedido = new Pedido("1", "Cliente Teste", 100.0);
        when(repository.existePorId("1")).thenReturn(false);

        PedidoDTO dto = service.criarPedido(pedido);

        assertNotNull(dto);
        assertEquals("Cliente Teste", dto.cliente());
        verify(repository).salvar(pedido);
        verify(publisher).enviarPedido(pedido);
    }

    @Test
    void deveLancarExcecaoSePedidoDuplicado() {
        Pedido pedido = new Pedido("1", "Cliente Teste", 100.0);
        when(repository.existePorId("1")).thenReturn(true);

        assertThrows(IllegalStateException.class, () -> service.criarPedido(pedido));
        verify(repository, never()).salvar(any());
        verify(publisher, never()).enviarPedido(any());
    }

    @Test
    void deveLancarExcecaoSeClienteInvalido() {
        Pedido pedido = new Pedido("1", "", 100.0);
        when(repository.existePorId("1")).thenReturn(false);

        assertThrows(MethodArgumentNotValidException.class, () -> service.criarPedido(pedido));
        verify(repository, never()).salvar(any());
        verify(publisher, never()).enviarPedido(any());
    }
}
