package com.rafael.pedido.publisher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.rafael.pedido.dto.PedidoDTO;
import com.rafael.pedido.model.Pedido;

public class PedidoPublisherTest {

    private PedidoPublisher publisher = new PedidoPublisher();

    @Test
    void deveRetornarDtoQuandoClienteValido() {
        Pedido pedido = new Pedido("1", "Cliente", 200.0);

        PedidoDTO dto = publisher.enviarPedido(pedido);

        assertEquals("Cliente", dto.getCliente());
    }

    @Test
    void deveLancarExcecaoSeClienteInvalido() {
        Pedido pedido = new Pedido("1", "", 200.0);

        assertThrows(IllegalArgumentException.class, () -> publisher.enviarPedido(pedido));
    }
}