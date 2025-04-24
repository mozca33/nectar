package com.rafael.pedido.publisher;

import org.springframework.stereotype.Component;

import com.rafael.pedido.model.Pedido;

/**
 * Classe responsável por publicar pedidos em um sistema externo.
 * Esta classe simula o envio de pedidos para um sistema externo, como RabbitMQ
 * ou Kafka.
 */
@Component
public class PedidoPublisher {

    /**
     * Método responsável por enviar um pedido para um sistema externo.
     * Neste exemplo, o envio é simulado e não há implementação real.
     *
     * @param pedido O pedido a ser enviado.
     */
    public void enviarPedido(Pedido pedido) {
        // Lógica de envio do pedido para um sistema externo ( RabbitMQ, Kafka, etc.)
    }
}