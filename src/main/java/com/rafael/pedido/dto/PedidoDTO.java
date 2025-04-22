package com.rafael.pedido.dto;

import com.rafael.pedido.model.Pedido;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class PedidoDTO {
    @NotNull(message = "ID não pode ser nulo.")
    private String id;

    @NotNull(message = "Cliente não pode ser nulo.")
    private String cliente;

    @Min(value = 0, message = "O valor total deve ser maior que zero.")
    private double valorTotal;

    public PedidoDTO() {
    }

    public PedidoDTO(Pedido pedido) {
        this.id = pedido.getId();
        this.cliente = pedido.getCliente();
        this.valorTotal = pedido.getValorTotal();
    }

    public PedidoDTO(String id, String cliente, double valorTotal) {
        this.id = id;
        this.cliente = cliente;
        this.valorTotal = valorTotal;
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

    public static PedidoDTO fromEntity(Pedido pedido) {
        return new PedidoDTO(pedido.getId(), pedido.getCliente(), pedido.getValorTotal());
    }

}
