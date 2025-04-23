package com.rafael.pedido.dto;

import com.rafael.pedido.model.Pedido;

public class PedidoDTO {
    private String id;
    private String cliente;
    private double valorTotal;

    public PedidoDTO() {
    }

    public PedidoDTO(Pedido pedido) {
        this.id = pedido.getId();
        this.cliente = pedido.getCliente();
        this.valorTotal = pedido.getValorTotal();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Pedido toEntity() {
        return new Pedido(id, cliente, valorTotal);
    }
}
