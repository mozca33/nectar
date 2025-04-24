package com.rafael.pedido.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rafael.pedido.dto.PedidoDTO;
import com.rafael.pedido.model.Pedido;
import com.rafael.pedido.service.PedidoService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService service;

    public PedidoController(PedidoService pedidoService) {
        this.service = pedidoService;
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> criarPedido(@Valid @RequestBody Pedido pedido) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarPedido(pedido));
    }

    @GetMapping("/{idPedido}")
    public ResponseEntity<PedidoDTO> consultarPedido(@PathVariable @NotBlank String idPedido) {
        return ResponseEntity.ok(service.consultar(idPedido));
    }
}