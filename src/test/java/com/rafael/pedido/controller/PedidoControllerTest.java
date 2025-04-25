package com.rafael.pedido.controller;

import com.rafael.pedido.dto.PedidoDTO;
import com.rafael.pedido.service.PedidoService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.NoSuchElementException;

/**
 * Classe de teste para o controlador de pedidos.
 * Verifica se os endpoints estão funcionando corretamente e se as validações
 * estão sendo aplicadas.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class PedidoControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private PedidoService service;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
    }

    /**
     * Testa a criação de um pedido com dados válidos.
     * Verifica se o status da resposta é 201 Created e se os dados do pedido
     * estão corretos.
     */
    @Test
    public void deveCriarPedido_quandoDadosValidos() throws Exception {
        PedidoDTO pedidoDTO = new PedidoDTO("1", "Cliente", 10.0);

        when(service.criarPedido(any())).thenReturn(pedidoDTO);

        mockMvc.perform(post("/pedidos")
                .content(objectMapper.writeValueAsString(pedidoDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.cliente").value("Cliente"))
                .andExpect(jsonPath("$.valorTotal").value("10.0"));
    }

    /**
     * Testa a consulta de um pedido existente.
     * Verifica se o status da resposta é 200 OK e se os dados do pedido estão
     * corretos.
     */
    @Test
    public void deveRetornarPedido_quandoPedidoExistente() throws Exception {
        PedidoDTO pedidoDTO = new PedidoDTO("1", "Cliente", 500.0);

        when(service.consultar("1")).thenReturn(pedidoDTO);

        mockMvc.perform(get("/pedidos/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.cliente").value("Cliente"))
                .andExpect(jsonPath("$.valorTotal").value("500.0"));
    }

    /**
     * Testa a consulta de um pedido inexistente.
     * Verifica se o status da resposta é 422 Unprocessable Entity.
     */
    @Test
    public void deveRetornar422_quandoIdForNulo() throws Exception {
        PedidoDTO pedidoDTO = new PedidoDTO(null, "Cliente", 1000.0);

        when(service.criarPedido(any())).thenThrow(new IllegalArgumentException());

        mockMvc.perform(post("/pedidos")
                .content(objectMapper.writeValueAsString(pedidoDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.status").value(422));
    }

    /**
     * Testa a criação de um pedido com ID inválido.
     * Verifica se o status da resposta é 422 Unprocessable Entity.
     */
    @Test
    void deveRetornar422_quandoClienteForVazio() throws Exception {
        PedidoDTO pedidoDTO = new PedidoDTO("1", "", 150.0);

        mockMvc.perform(post("/pedidos")
                .content(objectMapper.writeValueAsString(pedidoDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.status").value(422));
    }

    /**
     * Testa a criação de um pedido com valor total inválido.
     * Verifica se o status da resposta é 422 Unprocessable Entity.
     */
    @Test
    void deveRetornar422_quandoValorTotalForNegativo() throws Exception {
        PedidoDTO pedidoDTO = new PedidoDTO("1", "Cliente", -120.0);

        mockMvc.perform(post("/pedidos")
                .content(objectMapper.writeValueAsString(pedidoDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.status").value(422));
    }

    /**
     * Testa a criação de um pedido com todos os campos inválidos.
     * Verifica se o status da resposta é 422 Unprocessable Entity.
     */
    @Test
    void deveRetornar422_quandoIdEClienteForemInvalidos() throws Exception {
        PedidoDTO pedidoDTO = new PedidoDTO("", "", 200.0);

        mockMvc.perform(post("/pedidos")
                .content(objectMapper.writeValueAsString(pedidoDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    /**
     * Testa a criação de um pedido com ID e valor total inválidos.
     * Verifica se o status da resposta é 422 Unprocessable Entity.
     */
    @Test
    void deveRetornar422_quandoClienteEValorTotalForemInvalidos() throws Exception {
        PedidoDTO pedidoDTO = new PedidoDTO("1", "", 0.0);

        mockMvc.perform(post("/pedidos")
                .content(objectMapper.writeValueAsString(pedidoDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.status").value(422));
    }

    /**
     * Testa a criação de um pedido com cliente e valor total inválidos.
     * Verifica se o status da resposta é 422 Unprocessable Entity.
     */
    @Test
    void deveRetornar422_quandoTodosOsCamposForemInvalidos() throws Exception {
        PedidoDTO pedidoDTO = new PedidoDTO("", null, -0.0);

        mockMvc.perform(post("/pedidos")
                .content(objectMapper.writeValueAsString(pedidoDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.status").value(422));
    }

    /**
     * Testa a consulta de um pedido com ID inválido.
     * Verifica se o status da resposta é 422 Unprocessable Entity.
     */
    @Test
    public void deveRetornar422_quandoIdForVazio() throws Exception {
        when(service.consultar(" "))
                .thenThrow(new NoSuchElementException("Pedido não encontrado com o ID informado"));

        mockMvc.perform(get("/pedidos/ ")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    /**
     * Testa a consulta de um pedido com ID nulo.
     * Verifica se o status da resposta é 404 Not Found.
     */
    @Test
    public void deveRetornar404_quandoIdNaoExistir() throws Exception {
        when(service.consultar("999"))
                .thenThrow(new NoSuchElementException("Pedido não encontrado com o ID informado"));

        mockMvc.perform(get("/pedidos/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

}
