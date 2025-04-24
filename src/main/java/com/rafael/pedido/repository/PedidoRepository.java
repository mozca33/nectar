package com.rafael.pedido.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.rafael.pedido.model.Pedido;

/**
 * Classe responsável por gerenciar o armazenamento de pedidos.
 * Esta classe simula um banco de dados em memória para armazenar os pedidos.
 */
@Repository
public class PedidoRepository {

    private final Map<String, Pedido> banco = new HashMap<>();

    /**
     * Método responsável por salvar um pedido no repositório.
     *
     * @param pedido O pedido a ser salvo.
     */
    public void salvar(Pedido pedido) {
        banco.put(pedido.getId(), pedido);
    }

    /**
     * Método responsável por consultar um pedido pelo ID.
     *
     * @param idPedido O ID do pedido a ser consultado.
     * @return Um Optional contendo o pedido, se encontrado, ou vazio caso
     *         contrário.
     */
    public Optional<Pedido> consultar(String idPedido) {
        return Optional.ofNullable(banco.get(idPedido));
    }

    /**
     * Método responsável por verificar se um pedido existe pelo ID.
     *
     * @param idPedido O ID do pedido a ser verificado.
     * @return True se o pedido existir, false caso contrário.
     */
    public boolean existePorId(String idPedido) {
        return banco.containsKey(idPedido);
    }
}