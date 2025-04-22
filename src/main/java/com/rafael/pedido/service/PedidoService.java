package com.rafael.pedido.service;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.rafael.pedido.dto.PedidoDTO;
import com.rafael.pedido.model.Pedido;
import com.rafael.pedido.publisher.PedidoPublisher;
import com.rafael.pedido.repository.PedidoRepository;
import com.rafael.pedido.validator.PedidoValidator;

@Service
public class PedidoService {

    private final PedidoRepository repositorio;
    private final PedidoPublisher publicador;
    private final PedidoValidator validador;

    public PedidoService(PedidoRepository pedidoRepository, PedidoPublisher pedidoPublisher,
            PedidoValidator pedidoValidator) {
        this.repositorio = pedidoRepository;
        this.publicador = pedidoPublisher;
        this.validador = pedidoValidator;
    }

    public PedidoDTO criarPedido(Pedido pedido) {
        validador.validarPedido(pedido);
        if (repositorio.existePedidoPorId(pedido.getId())) {
            throw new IllegalStateException("Pedido já existe com o ID informado.");
        }

        repositorio.salvar(pedido);
        publicador.enviarPedido(pedido);

        return new PedidoDTO(pedido);
    }

    public PedidoDTO atualizar(String idPedido, Pedido pedido) {
        validador.validarPedido(pedido);
        if (!repositorio.existePedidoPorId(idPedido)) {
            throw new NoSuchElementException("Pedido com ID '" + idPedido + "' não encontrado.");
        }

        pedido.setId(idPedido);
        repositorio.salvar(pedido);

        return new PedidoDTO(pedido);
    }

    public PedidoDTO consultarPedido(String idPedido) {

        Pedido pedido = repositorio.consultar(idPedido);

        if (pedido == null) {
            throw new NoSuchElementException("Pedido com ID '" + idPedido + "' não encontrado.");
        }

        return new PedidoDTO(pedido);
    }

    public void remover(String idPedido) {
        if (!repositorio.existePedidoPorId(idPedido)) {
            throw new NoSuchElementException("Não é possível remover o pedido com ID '" + idPedido + "'");
        }

        repositorio.remover(idPedido);
    }

}