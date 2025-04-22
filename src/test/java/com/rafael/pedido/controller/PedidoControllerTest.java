package com.rafael.pedido.controller;

import com.rafael.pedido.dto.PedidoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PedidoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testCriarPedido() throws Exception {
        PedidoDTO pedidoDTO = new PedidoDTO("1", "Cliente", 100.0);

        mockMvc.perform(post("/pedidos")
                .content(objectMapper.writeValueAsString(pedidoDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.cliente").value("Cliente"))
                .andExpect(jsonPath("$.valorTotal").value("100.0"));
    }

    @Test
    public void testConsultarPedido() throws Exception {
        testCriarPedido();
        mockMvc.perform(get("/pedidos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.cliente").value("Cliente"))
                .andExpect(jsonPath("$.valorTotal").value("100.0"));
    }

    @Test
    public void testAtualizarPedido() throws Exception {
        PedidoDTO pedidoDTO = new PedidoDTO("12", "Cliente Att", 2000.0);
        testCriarPedido();
        mockMvc.perform(put("/pedidos/1")
                .content(objectMapper.writeValueAsString(pedidoDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.cliente").value("Cliente Att"))
                .andExpect(jsonPath("$.valorTotal").value("2000.0"));
    }

    @Test
    public void testPedidoNotFound() throws Exception {
        mockMvc.perform(get("/pedidos/999"))
                .andExpect(status().isNotFound());
    }

}
