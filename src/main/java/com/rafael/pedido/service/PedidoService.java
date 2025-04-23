package com.rafael.pedido.service;

import org.springframework.stereotype.Service;

import com.rafael.pedido.dto.PedidoDTO;
import com.rafael.pedido.model.Pedido;
import com.rafael.pedido.publisher.PedidoPublisher;
import com.rafael.pedido.repository.PedidoRepository;

@Service
public class PedidoService {

    private final PedidoRepository repositorio;
    private final PedidoPublisher publicador;

    public PedidoService(PedidoRepository pedidoRepository, PedidoPublisher pedidoPublisher) {
        this.repositorio = pedidoRepository;
        this.publicador = pedidoPublisher;
    }

    public PedidoDTO criarPedido(Pedido pedido) {
        verificarPedidoDuplicado(pedido);
        repositorio.salvar(pedido);
        publicador.enviarPedido(pedido);

        return new PedidoDTO(pedido);
    }

    private void verificarPedidoDuplicado(Pedido pedido) {
        if (repositorio.existePedidoPorId(pedido.getId())) {
            throw new IllegalStateException("Pedido já existe com o ID informado.");
        }
    }

}