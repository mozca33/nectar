# 💻 Sistema de Processamento de Pedidos

Este projeto faz parte de um case técnico com o objetivo de identificar e corrigir bugs em um sistema legado de geração, envio e consulta de pedidos. O foco está em compreender, corrigir e melhorar a estrutura existente, respeitando as regras de negócio definidas.

# ⚙️ Ambiente de Desenvolvimento Utilizado

- **Java**: version 21.0.5.
- **Spring Boot**: version 3.4.4.
- **Ferramenta de Build**: Apache Maven 3.9.9
- **IDE recomendada**: VS Code + Extensões.
- **Ferramenta para teste de API**: Postman v11.42.3.

# 🚀 Como Rodar o Projeto

1. Extraia o arquivo .zip ou clone o repositório.
2. Importe o projeto em sua IDE de preferência (IntelliJ, Eclipse ou VS Code).
3. Certifique-se de que o ambiente está corretamente configurado dependendo da IDE escolhida.
4. Execute a aplicação usando sua IDE ou via terminal com o comando:
```bash
   mvn spring-boot:run
```
5. Utilize uma ferramenta como o Postman para testes.

---

## 🪲 Problemas Identificados e Corrigidos

### Bug 1 - Valor Negativo Sendo Aceito na Criação do Pedido

**Problema**:
O sistema permite o cadastro de pedidos com valor total negativo ou nulo, devido a uma falha lógica na verificação do valor do pedido na classe **PedidoService.java**. Se o valorTotal for menor que zero o sistema imprime **"Valor negativo..."** e segue com a persistência e envio do pedido.

**Correção**:
Foi adicionado a validação `@Positive` da anotação jakarta.validation.constraints.Positive na classe **Pedido.java**, garantindo que o valor total seja positivo antes de o pedido ser processado. Isso impede a criação de pedidos com valores negativos.
```bash
@Positive(message = "Valor total deve ser positivo.")
    private double valorTotal;
```
---

### Bug 2 - Falha no Envio Para a Fila

**Problema**:
O sistema não está enviando mensagens corretamente para a fila em algumas situações onde o campo Id do pedido é vazio ou nulo, a mensagem enviada à fila fica sem conteúdo:
Enviando para a fila: 

**Correção**:
Foi adicionado a validação `@NotBlank` da anotação jakarta.validation.constraints.NotBlank na classe **Pedido.java** para garantir que o campo Id não seja nulo ou vazio, evitando o envio incorreto para a fila.
```bash
@NotBlank(message = "ID do pedido não pode ser nulo ou vazio.")
    private String id;
```
---

### Bug 3 - Consulta Incorreta de Pedidos

**Problema**:
O sistema não retorna corretamente o pedido quando consultado pelo ID. Isso ocorre devido à implementação na classe **PedidoRepository.java** que para cada consulta está sendo instanciado um novo pedido usando o Id passado como parâmetro. Vale ressaltar que também há a falta da implementação do método consultar da classe **PedidoService.java**.

**Correção**:
Foi removida a instanciação de um novo pedido utilizando o idPedido.
Foi implementado também um mecanismo simulado de armazenamento com `Map<String, Pedido>` para persistência temporária dos pedidos enquanto a aplicação estiver em execução.
O método consultar agora retorna o pedido real armazenado.

```bash
private final Map<String, Pedido> banco = new HashMap<>();

    public void salvar(Pedido pedido) {
        banco.put(pedido.getId(), pedido);
    }

    public Optional<Pedido> consultar(String idPedido) {
        return Optional.ofNullable(banco.get(idPedido));
    }
```
---

### Bug 4 - Violação da Regra de Negócio

**Problema**:
A lógica de processamento não valida corretamente se o pedido é válido, isto é, se os campos id, cliente e valorTotal estão corretamente preenchidos.

**Correção**:
Foram feitas validações completas utilizando `@NotBlank` e `@Positive` garantindo que apenas pedidos válidos sejam processados. Agora, o sistema rejeita pedidos com os campos id, cliente nulos ou vazios e valorTotal negativo ou zerado.

# 📗 Melhorias Implementadas

1. Refatoração da classe `PedidoController.java`:
   Seguiu-se as boas práticas REST, com uso de `ResponseEntity` para definir respostas mais claras e estruturadas. O retorno agora é tipado com um PedidoDTO.
   
2. Refatoração da classe `PedidoService.java`:
   O código foi organizado para centralizar a lógica de validação e processamento de pedidos. A validação do pedido foi encapsulada em métodos próprios, e a conversão dos dados foi organizada com o uso de DTOs.
   
3. Refatoração da classe `PedidoRepository.java`:
   Foi criado um mecanismo simples de persistência usando `HashMap` para armazenar e consultar pedidos. Além disso, foi implementado um método para verificar se o pedido já existe antes de salvar um novo.
   
4. Refatoração da classe `PedidoPublisher.java`:
   O método de envio de pedidos para a fila foi simplificado. A persistência dos pedidos foi removida do publisher, e agora o método apenas envia o pedido para a fila. O PedidoDTO agora é retornado em vez de um objeto de domínio diretamente.
   
5. Refatoração da classe `Pedido.java`:
   Foram aplicadas as anotações de validação do Jakarta Validation, como `@NotBlank` e `@Positive`, para garantir que os campos obrigatórios não sejam nulos ou vazios, e o valorTotal do pedido seja positivo.
   
6. Criação da classe `GlobalExceptionHandler.java`:
   Foi criada uma tratativa de exceções com a anotação `@RestControllerAdvice` para poder capturar e tratar exceções de forma global, centralizada e customizada, retornando respostas adequadas para cada exceção.

# 📗 Melhorias Sugeridas (Não Implementadas)

1. Criar um arquivo de mensagens de erro para centralizar e padronizar todas as mensagens, ajudando na legibilidade, reutiilização e mantenibilidade (Ou futura internacionalização).
   
2. Utilizar UUID para id do pedido em vez de uma String, garantindo que cada pedido seja único.

3. Implementar um [Fluxo CRUD](#fluxo-crud) completo para gerenciamento apropriado de pedidos.

4. Implementar um campo status do pedido para acompanhamento de pedidos enviados e não-enviados. Poderá servir também como sinalização de pedidos já salvos que estão inconsistentes, para que sejam corrigidos manualmente ou tratados de maneira diferente durante a consulta e outras futuras funcionalidades através de um status especial inconsistente.

5. Implementar um processo de migração para corrigir pedidos existentes que possam estar inconsistentes no banco (com Id, cliente ou valorTotal inválidos).

# 📌 Considerações finais

O sistema foi ajustado para seguir a regra de negócio e garantir integridade nos dados de pedidos. Os principais bugs foram corrigidos com foco em clareza e manutenção. O código foi refatorado de maneira a seguir melhores práticas, com o uso de DTOs, Validações de Campo, e a separação de responsabilidades em cada camada, garantindo que a aplicação seja fácil de entender e de manter no futuro.

## Fluxo CRUD

### Exemplo de implementação no PedidoController.java
```bash
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerPedido(@PathVariable("id") String idPedido) {
        service.remover(idPedido);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoDTO> atualizarPedido(@PathVariable("id") String idPedido, @RequestBody Pedido pedido) {
        return ResponseEntity.ok(service.atualizar(idPedido, pedido));
    }
```
### Exemplo de implementação no PedidoService.java
```bash
    public PedidoDTO atualizar(String idPedido, Pedido pedido) {
        if (!repositorio.existePedidoPorId(idPedido)) {
            throw new NoSuchElementException("Pedido com ID informado não encontrado.");
        }

        pedido.setId(idPedido);
        repositorio.salvar(pedido);

        return new PedidoDTO(pedido);
    }

    public void remover(String idPedido) {
        if (!repositorio.existePedidoPorId(idPedido)) {
            throw new NoSuchElementException("Não é possível remover o pedido com ID informado.");
        }

        repositorio.remover(idPedido);
    }
```
### Exemplo de implementação no PedidoRepository.java
```bash
   public void remover(String idPedido) {
      database.remove(idPedido);
   }
``` 
