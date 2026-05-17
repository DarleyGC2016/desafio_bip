# ☕ Backend - Gestor de Benefícios (Spring Boot)

Este é o módulo de inteligência e regras de negócio do sistema, desenvolvido em Java utilizando o ecossistema Spring Boot. A API foi desenhada seguindo os princípios RESTful, com validações de dados centralizadas e documentação viva.

---

## 🛠️ Tecnologias e Arquitetura

- **Java 17 / 21** & **Spring Boot** — Core do desenvolvimento.
- **Spring Data JPA** — Camada de persistência e comunicação com o banco de dados.
- **H2 Database / PostgreSQL** — Banco de dados relacional para armazenamento das entidades.
- **Validation (Hibernate Validator)** — Annotations (`@Valid`, `@NotNull`, `@Min`) para garantir que as regras de negócio de saldo e campos sejam validadas antes de chegar ao banco.
- **Service Pattern & DTOs** — Separação clara de responsabilidades, garantindo que as entidades de banco de dados não sejam expostas diretamente na API.

---

## 📋 Endpoints Principais (API REST)

A API possui os seguintes recursos mapeados:

- `GET /api/v1/beneficios` — Listagem pagipada e ordenada de benefícios.
- `GET /api/v1/beneficios/{id}` — Busca detalhada de um único registro.
- `POST /api/v1/beneficios` — Cadastro de novas regras de benefícios.
- `PUT /api/v1/beneficios/editar/{id}` — Atualização cadastral (Nome, Descrição, Valor).
- `PUT /api/v1/beneficios/transferir` — **Regra de Negócio Core:** Realiza a transferência de saldos validando se o benefício de origem possui fundos suficientes.
- `DELETE /api/v1/beneficios/excluir/{id}` — Exclusão lógica ou física do benefício.

---

## 📖 Documentação da API (Swagger)

A API conta com a documentação do **Swagger/OpenAPI**. Com a aplicação rodando, você pode testar todos os endpoints diretamente pelo navegador acessando:
🔗 `http://localhost:8080/swagger-ui/index.html`

---

## ⚙️ Como Executar o Servidor

### **Pré-requisitos**

```bash
- Java JDK 17 ou superior instalado.
- Maven 3.x instalado.
```

### **Comandos para Inicialização**

Abra o seu terminal dentro desta pasta do backend e execute:

```bash
# Compilar o projeto e baixar as dependências do pom.xml
mvn clean install

# Executar a aplicação Spring Boot
mvn spring-boot:run
```

O servidor será iniciado na porta **8080**. Os logs de inicialização e conexões serão exibidos diretamente no terminal.

- **PostgreSQL** — Banco de dados relacional robusto utilizado em ambiente de desenvolvimento/produção para garantir a persistência e integridade dos dados da aplicação.
- **H2 Database** — Banco de dados em memória configurado exclusivamente para o ambiente de **Testes Unitários e de Integração**, garantindo isolamento completo e execuções de testes em alta velocidade, sem afetar os dados reais.
