package com.rafael.pedido.model;

/**
 * Representa um pedido no sistema.
 * Contém as informações do pedido, como o valor total, status e dados do
 * cliente.
 * A classe também valida a consistência dos dados antes de permitir que o
 * pedido seja criado.
 */
public class Pedido {
    private String id;
    private String cliente;
    private double valorTotal;

    public Pedido() {
    }

    /**
     * Construtor para inicializar um pedido com os dados fornecidos.
     * 
     * @param id         O identificador do pedido.
     * @param cliente    O nome do cliente associado ao pedido.
     * @param valorTotal O valor total do pedido.
     */
    public Pedido(String id, String cliente, double valorTotal) {
        this.id = id;
        this.cliente = cliente;
        this.valorTotal = valorTotal;
    }

    /**
     * Construtor para inicializar um pedido com apenas o ID.
     * 
     * @param id O identificador do pedido.
     */
    public Pedido(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getCliente() {
        return cliente;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }
}