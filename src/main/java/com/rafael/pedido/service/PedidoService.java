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
        verificarPedidoJaExistente(pedido.getId());
        repositorio.salvar(pedido);
        publicador.enviarPedido(pedido);

        return new PedidoDTO(pedido);
    }

    public PedidoDTO consultar(String idPedido) {
        Pedido pedido = repositorio.consultar(idPedido)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado com o ID informado."));

        return new PedidoDTO(pedido);
    }

    private void verificarPedidoJaExistente(String idPedido) {
        if (repositorio.existePorId(idPedido)) {
            throw new IllegalArgumentException("Pedido já existe com o ID informado.");
        }
    }
}