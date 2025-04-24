package com.rafael.pedido.service;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.rafael.pedido.dto.PedidoDTO;
import com.rafael.pedido.model.Pedido;
import com.rafael.pedido.publisher.PedidoPublisher;
import com.rafael.pedido.repository.PedidoRepository;

/**
 * Classe responsável por gerenciar a lógica de negócios relacionada aos
 * pedidos.
 * Esta classe contém métodos para criar e consultar pedidos.
 */
@Service
public class PedidoService {

    private final PedidoRepository repositorio;
    private final PedidoPublisher publicador;

    /**
     * Construtor do serviço de pedidos.
     *
     * @param pedidoRepository o repositório responsável por persistir os pedidos.
     * @param pedidoPublisher  o publicador responsável por enviar os pedidos para
     *                         sistemas externos.
     */
    public PedidoService(PedidoRepository pedidoRepository, PedidoPublisher pedidoPublisher) {
        this.repositorio = pedidoRepository;
        this.publicador = pedidoPublisher;
    }

    /**
     * Método responsável por criar um novo pedido.
     *
     * @param pedidoDTO o pedido a ser criado.
     * @return Um PedidoDTO do pedido criado.
     */
    public PedidoDTO criarPedido(PedidoDTO pedidoDTO) {
        Pedido pedido = pedidoDTO.toEntity();
        verificarPedidoJaExistente(pedido.getId());
        repositorio.salvar(pedido);
        publicador.enviarPedido(pedido);

        return new PedidoDTO(pedido);
    }

    /**
     * Método responsável por consultar um pedido existente pelo seu ID.
     *
     * @param idPedido o identificador do pedido a ser consultado.
     * @return Um PedidoDTO do pedido contendo os dados encontrados.
     */
    public PedidoDTO consultar(String idPedido) {
        Pedido pedido = repositorio.consultar(idPedido)
                .orElseThrow(() -> new NoSuchElementException("Pedido não encontrado com o ID informado."));

        return new PedidoDTO(pedido);
    }

    /**
     * Método responsável por verificar se um pedido já existe no banco pelo ID.
     *
     * @param idPedido O ID do pedido a ser verificado.
     */
    private void verificarPedidoJaExistente(String idPedido) {
        if (repositorio.existePorId(idPedido)) {
            throw new IllegalArgumentException("Pedido já existe com o ID informado.");
        }
    }
}