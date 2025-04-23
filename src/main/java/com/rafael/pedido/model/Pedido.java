package com.rafael.pedido.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class Pedido {
    @NotBlank(message = "ID do pedido não pode ser nulo ou vazio.")
    private String id;

    @NotBlank(message = "Cliente não pode ser nulo ou vazio.")
    private String cliente;

    @Positive(message = "Valor total deve ser positivo.")
    private double valorTotal;

    public Pedido() {
    }

    public Pedido(String id, String cliente, double valorTotal) {
        this.id = id;
        this.cliente = cliente;
        this.valorTotal = valorTotal;
    }

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