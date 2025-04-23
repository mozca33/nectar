# 💻 Sistema de Processamento de Pedidos

Este projeto é parte de um case técnico cujo objetivo é corrigir bugs em um sistema legado de geração, envio e consulta de pedidos.
O foco está em compreender, corrigir e melhorar a estrutura existente, respeitando as regras de negócio propostas.

# 🚀 Como rodar o projeto

1. Extraia o arquivo `.zip` ou clone o repositório.
2. Importe o projeto em sua IDE de preferência.
3. Certifique-se de que o Java está corretamente configurado no ambiente.
4. Execute a aplicação com o Spring Boot.
5. Utilize ferramentas como Postman ou Insomnia para testar os endpoints.

---

## 🪲 Problemas identificados e corrigidos

### Bug 1 - Valor negativo sendo aceito na criação do pedido

Problema:
O sistema permitia o cadastro de pedidos com valor total negativo ou nulo violando a regra de negócio.

Correção:
Foi adicionado uma validação no método `processarPedido` que lança exceção se o 'valorTotal' for menor ou igual a zero.

### Bug 2 - Falha no envio para a fila

Problema:
O sistema não enviava corretamente o pedido para a fila em alguns casos. A lógica estava incompleta e o envio dependia apenas da existência do cliente.

Correção:
Foi adicionado uma validação completa no método `enviarPedido` e retirado trechos duplicados de salvamento.

### Bug 3 - Consulta incorreta de pedidos

Problema:
O método consultar do repositório estava instanciando um novo pedido com base no ID, o que gerava dados incorretos.

Correção:
Foi implementado um mecanismo simulado de armazenamento com Map<String, Pedido> para persistência e consulta real dos dados. O método consultar agora retorna o pedido real salvo.

### Bug 4 - Violação da regra de negócio

Problema:
A lógica de processamento não checava corretamente se o pedido era válido, isto é, se os campos `id`, `cliente` e `valorTotal` eram corretamente preenchidos.

Correção:
Foram feitas validações completas garantindo que apenas pedidos válidos são salvos e enviados, ou seja, pedidos com os campos `id`, `cliente` e `valorTotal` não nulos e não vazios.

# 📗 Melhorias Sugeridas (Não implementadas)

1. Criar um arquivo de mensagens de erro padronizadas, ajudando na legibilidade, reutiilização e mantenibilidade (Ou futura internacionalização).
2. Mudar o Id do pedido de String para UUID, garantindo que cada pedido seja único, evitando verificações de duplicidade.
3.

# 📌 Considerações finais

O sistema foi ajustado para seguir a regra de negócio e garantir integridade nos dados de pedidos. Os principais bugs foram corrigidos com foco em clareza e manutenção. O código pode ser evoluído com camadas mais bem definidas e testes automatizados para garantir maior confiabilidade.

    -> utilizar Constraints no lugar da classe de validação para tornar o código menos verboso.
    public class PedidoDTO {
        @NotNull(message = "ID não pode ser nulo.")
        @NotBlank(message = "ID não pode ser em branco.")
        private String id;

        @NotNull(message = "Cliente não pode ser nulo.")
        @NotBlank(message = "ID não pode ser em branco.")
        private String cliente;

        @Min(value = 0, message = "O valor total deve ser maior que zero.")
        @NotBlank(message = "ID não pode ser em branco.")
        private double valorTotal;
    }


    -> Criação de testes unitários

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

    -> Criação de novas funcionalidades para controle do usuário sobre o sistema no PedidoController.java

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> consultarPedido(@PathVariable("id") String idPedido) {
        return ResponseEntity.ok(service.consultarPedido(idPedido));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerPedido(@PathVariable("id") String idPedido) {
        service.remover(idPedido);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoDTO> atualizarPedido(@PathVariable("id") String idPedido, @RequestBody Pedido pedido) {
        return ResponseEntity.ok(service.atualizar(idPedido, pedido));
    }

    -> Criação de novas funcionalidades para controle do usuário sobre o sistema no PedidoService.java

    public PedidoDTO atualizar(String idPedido, Pedido pedido) {
        validador.validarPedido(pedido);
        if (!repositorio.existePedidoPorId(idPedido)) {
            throw new NoSuchElementException("Pedido com ID '" + idPedido + "' não encontrado.");
        }

        pedido.setId(idPedido);
        repositorio.salvar(pedido);

        return new PedidoDTO(pedido);
    }

    public PedidoDTO consultarPedido(String idPedido) {

        Pedido pedido = repositorio.consultar(idPedido);

        if (pedido == null) {
            throw new NoSuchElementException("Pedido com ID '" + idPedido + "' não encontrado.");
        }

        return new PedidoDTO(pedido);
    }

    public void remover(String idPedido) {
        if (!repositorio.existePedidoPorId(idPedido)) {
            throw new NoSuchElementException("Não é possível remover o pedido com ID '" + idPedido + "'");
        }

        repositorio.remover(idPedido);
    }


    -> Criação de novas funcionalidades para controle do usuário sobre o sistema no PedidoService.java
    public void remover(String idPedido) {
        database.remove(idPedido);
    }
