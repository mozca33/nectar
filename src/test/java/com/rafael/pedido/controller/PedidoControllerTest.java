package com.rafael.pedido.controller;

import com.rafael.pedido.dto.PedidoDTO;
import com.rafael.pedido.model.Pedido;
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
        Pedido pedido = new Pedido("1", "Cliente", 100.0);
        PedidoDTO pedidoDTO = new PedidoDTO(pedido);

        when(service.criarPedido(any())).thenReturn(pedidoDTO);

        mockMvc.perform(post("/pedidos")
                .content(objectMapper.writeValueAsString(pedido))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.cliente").value("Cliente"))
                .andExpect(jsonPath("$.valorTotal").value("100.0"));
    }

    /**
     * Testa a consulta de um pedido inexistente.
     * Verifica se o status da resposta é 404 Not Found.
     */
    @Test
    public void deveRetornar400_quandoIdForNulo() throws Exception {
        Pedido pedido = new Pedido(null, "Cliente", 100.0);
        PedidoDTO pedidoDTO = new PedidoDTO(pedido);

        when(service.criarPedido(any())).thenThrow(new IllegalArgumentException());

        mockMvc.perform(post("/pedidos")
                .content(objectMapper.writeValueAsString(pedidoDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    /**
     * Testa a criação de um pedido com ID inválido.
     * Verifica se o status da resposta é 400 Bad Request.
     */
    @Test
    void deveRetornar400_quandoClienteForVazio() throws Exception {
        PedidoDTO dto = new PedidoDTO("1", "", 100.0);

        mockMvc.perform(post("/pedidos")
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /**
     * Testa a criação de um pedido com valor total inválido.
     * Verifica se o status da resposta é 400 Bad Request.
     */
    @Test
    void deveRetornar400_quandoValorTotalForNegativo() throws Exception {
        PedidoDTO dto = new PedidoDTO("1", "Cliente", -100.0);

        mockMvc.perform(post("/pedidos")
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /**
     * Testa a criação de um pedido com todos os campos inválidos.
     * Verifica se o status da resposta é 400 Bad Request.
     */
    @Test
    void deveRetornar400_quandoIdEClienteForemInvalidos() throws Exception {
        PedidoDTO dto = new PedidoDTO("", "", 100.0);

        mockMvc.perform(post("/pedidos")
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /**
     * Testa a criação de um pedido com ID e valor total inválidos.
     * Verifica se o status da resposta é 400 Bad Request.
     */
    @Test
    void deveRetornar400_quandoClienteEValorTotalForemInvalidos() throws Exception {
        PedidoDTO dto = new PedidoDTO("1", "", -5.0);

        mockMvc.perform(post("/pedidos")
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /**
     * Testa a criação de um pedido com cliente e valor total inválidos.
     * Verifica se o status da resposta é 400 Bad Request.
     */
    @Test
    void deveRetornar400_quandoTodosOsCamposForemInvalidos() throws Exception {
        PedidoDTO dto = new PedidoDTO("", null, -1.0);

        mockMvc.perform(post("/pedidos")
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /**
     * Testa a consulta de um pedido com ID inválido.
     * Verifica se o status da resposta é 400 Bad Request.
     */
    @Test
    public void deveRetornar400_quandoIdForVazio() throws Exception {
        when(service.consultar(" "))
                .thenThrow(new NoSuchElementException("Pedido não encontrado com o ID informado"));

        mockMvc.perform(get("/pedidos/ ")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    /**
     * Testa a consulta de um pedido com ID nulo.
     * Verifica se o status da resposta é 400 Bad Request.
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
