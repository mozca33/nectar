package com.rafael.pedido.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rafael.pedido.dto.PedidoDTO;
import com.rafael.pedido.service.PedidoService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Controlador responsável por lidar com requisições relacionadas a pedidos.
 * Oferece endpoints para criação e consulta de pedidos.
 */
@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService service;

    /**
     * Construtor do controlador de pedidos.
     *
     * @param pedidoService o serviço responsável pelas regras de negócio dos
     *                      pedidos
     */
    public PedidoController(PedidoService pedidoService) {
        this.service = pedidoService;
    }

    /**
     * Endpoint para criar um novo pedido.
     *
     * @param pedidoDTO o pedido a ser criado.
     * @return o pedido criado,
     */
    @PostMapping
    public ResponseEntity<PedidoDTO> criarPedido(@Valid @RequestBody PedidoDTO pedidoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarPedido(pedidoDTO));
    }

    /**
     * Endpoint para consultar um pedido existente pelo seu ID.
     *
     * @param idPedido o identificador do pedido a ser consultado.
     * @return Os dados do pedido encontrado e status 200 OK.
     */
    @GetMapping("/{idPedido}")
    public ResponseEntity<PedidoDTO> consultarPedido(@PathVariable @NotBlank String idPedido) {
        return ResponseEntity.ok(service.consultar(idPedido));
    }
}