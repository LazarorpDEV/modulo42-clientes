# Microserviço de clientes

Projeto independente do módulo 42. Java 17+ (incluindo Java 25), Maven 3.6.3+, Spring Boot 3.5.16, Spring Web, JPA, Validation e H2.

## Executar

Abra um terminal **nesta pasta**, onde está o pom.xml:

```bash
mvn clean verify
mvn spring-boot:run
```

Alternativa após o build: `java -jar target/cliente-service-1.0.0.jar`.
O serviço atende em http://localhost:8081. O banco próprio fica em `data/` e mantém os registros após reiniciar. Os testes usam outro banco, em memória.

## API

| Método | Caminho | Função |
|---|---|---|
| POST | /api/clientes | Cadastrar (201 e cabeçalho Location) |
| PUT | /api/clientes/{id} | Alterar todos os campos (200) |
| GET | /api/clientes/{id} | Buscar por ID (200) |
| GET | /api/clientes | Listar todos |
| GET | /api/clientes?nome=parte | Pesquisar nome, sem diferenciar maiúsculas |

Corpo de POST e PUT (JSON):

```json
{
  "nome": "Lazaro",
  "email": "lazaro@example.com",
  "cpf": "12345678901"
}
```

No Postman, escolha Body → raw → JSON e envie para `http://localhost:8081/api/clientes`. Use o ID devolvido no PUT/GET. Navegar à raiz `/` não é uma página web: esta aplicação é uma API REST.

Campos inválidos retornam 400; ID ausente retorna 404; cpf repetido retorna 409. CPF aceita exatamente 11 dígitos, sem pontuação; não valida os dígitos verificadores.

## Arquitetura

Controller recebe HTTP e valida o DTO; Service aplica regras; Repository acessa a entidade JPA no banco local exclusivo. Não há dependência de código, banco ou execução do outro microserviço.

## Testes

`mvn clean verify` executa os testes de integração via HTTP com servidor em porta aleatória e banco de teste isolado. Cobertura: cadastro e persistência, busca por ID, alteração, pesquisa/listagem, validações, duplicidade e ID inexistente.

## GitHub

Crie um repositório vazio chamado `modulo42-clientes` (sem README automático). Dentro desta pasta execute:

```bash
git init
git add .
git commit -m "Implementa microservico de clientes do modulo 42"
git branch -M main
git remote add origin https://github.com/LazarorpDEV/modulo42-clientes.git
git push -u origin main
```

Se houver pedido de autenticação, entre na sua conta do GitHub. Os comandos não criam o repositório remoto.
