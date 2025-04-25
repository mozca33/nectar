package com.rafael.pedido.exception;

import com.rafael.pedido.service.PedidoService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Classe de teste para o tratamento global de exceções.
 * Verifica se as exceções lançadas na aplicação estão sendo tratadas
 * corretamente
 * e se as respostas estão no formato esperado.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class GlobalHandlerExceptionTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PedidoService pedidoService;

    /**
     * Testa o tratamento de exceções de validação de handler.
     * Verifica se o status da resposta é 400 Bad Request e se a mensagem
     * de erro
     * está correta.
     */
    @Test
    void deveRetornar400_paraExcecaoDeValidacaoDeHandler() throws Exception {
        mockMvc.perform(get("/pedidos/ "))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem").value("400 BAD_REQUEST \"Validation failure\""))
                .andExpect(jsonPath("$.status").value(400));
    }

    /**
     * Testa o tratamento de exceções de recurso não encontrado.
     * Verifica se o status da resposta é 400 Not Found e se a mensagem de erro
     * está correta.
     */
    @Test
    void deveRetornar400_paraExcecaoDeArgumentoIlegal() throws Exception {
        when(pedidoService.consultar("abc")).thenThrow(new IllegalArgumentException("ID inválido"));

        mockMvc.perform(get("/pedidos/abc"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem").value("ID inválido"))
                .andExpect(jsonPath("$.status").value(400));
    }

    /**
     * Testa o tratamento de exceções de estado ilegal.
     * Verifica se o status da resposta é 400 Bad Request e se a mensagem de erro
     * está correta.
     */
    @Test
    void deveRetornar400_paraExcecaoDeEstadoIlegal() throws Exception {
        when(pedidoService.consultar("2")).thenThrow(new IllegalStateException("Estado inválido"));

        mockMvc.perform(get("/pedidos/2"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem").value("Estado inválido"))
                .andExpect(jsonPath("$.status").value(400));
    }

    /**
     * Testa o tratamento de exceções de elemento não encontrado.
     * Verifica se o status da resposta é 404 Not Found e se a mensagem de erro
     * está correta.
     */
    @Test
    void deveRetornar404_paraExcecaoDeElementoNaoEncontrado() throws Exception {
        when(pedidoService.consultar("990")).thenThrow(new NoSuchElementException("Pedido não encontrado"));

        mockMvc.perform(get("/pedidos/990"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem").value("Pedido não encontrado"))
                .andExpect(jsonPath("$.status").value(404));
    }

    /**
     * Testa o tratamento de exceções de recurso não encontrado.
     * Verifica se o status da resposta é 404 Not Found e se a mensagem de erro
     * está correta.
     */
    @Test
    void deveRetornar404_paraExcecaoDeRecursoNaoEncontrado() throws Exception {

        mockMvc.perform(get("/pedidos/"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem").value("No static resource pedidos."))
                .andExpect(jsonPath("$.status").value(404));
    }

    /**
     * Testa o tratamento de exceções genéricas.
     * Verifica se o status da resposta é 500 Internal Server Error e se a mensagem
     * de erro está correta.
     */
    @Test
    void deveRetornar500_paraExcecaoGenerica() throws Exception {
        Mockito.when(pedidoService.consultar("1")).thenThrow(new RuntimeException("Erro interno"));

        mockMvc.perform(get("/pedidos/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.mensagem").value("Erro interno"))
                .andExpect(jsonPath("$.status").value(500));
    }

    /**
     * Testa o tratamento de exceções de validação de método.
     * Verifica se o status da resposta é 400 Bad Request e se a mensagem de erro
     * está correta.
     */
    @Test
    void deveRetornar422_paraExcecaoDeValidacaoDeMetodo() throws Exception {
        String jsonInvalido = """
                {
                    "id": null,
                    "cliente": null,
                    "valorTotal": -10.0
                }
                """;

        mockMvc.perform(post("/pedidos")
                .content(jsonInvalido)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.status").value(422));
    }
}