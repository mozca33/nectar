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

    @Test
    public void deveCriarPedido() throws Exception {
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

    @Test
    public void naoDeveCriarPedido() throws Exception {
        Pedido pedido = new Pedido("1", "Cliente", -100.0);
        PedidoDTO pedidoDTO = new PedidoDTO(pedido);

        when(service.criarPedido(any())).thenThrow(new IllegalArgumentException());

        mockMvc.perform(post("/pedidos")
                .content(objectMapper.writeValueAsString(pedidoDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }
}
