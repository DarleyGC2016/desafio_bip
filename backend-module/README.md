![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/SpringBoot-3.5.14-green)
![Lombok](https://img.shields.io/badge/Lombok-enabled-red)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-18-blue)
![JUnit 5](https://img.shields.io/badge/JUnit%205-enabled-success)
![Mockito](https://img.shields.io/badge/Mockito-enabled-brightgreen)
![JaCoCo](https://img.shields.io/badge/JaCoCo-enabled-yellowgreen)

# Backend Module

## 🗂️ Índice

- [☕ Resumo do Backend Module](#-resumo-do-backend-module)
- [🛠️ Tecnologias e Arquitetura](#️-tecnologias-e-arquitetura)
- [🧩 Arquitetura do Backend Module](#-arquitetura-do-backend-module)
- [📋 Endpoints (API REST)](#-endpoints-api-rest)
- [📝 Requisições de Endpoints](#-requisições-de-endpoints)
- [📖 Documentação da API (Swagger)](#-documentação-da-api-swagger)
- [⚙️ Como Executar o Servidor](#️-como-executar-o-servidor)
- [💡 Configuração de Ambiente](#-configuração-de-ambiente)
- [💡 Configuração de Ambiente para Testes](#-configuração-de-ambiente-para-testes)
- [📊 Cobertura dos Testes Unitários e Integração](#-cobertura-dos-testes-unitários-e-integração)

## ☕ Resumo do Backend Module
 
Este módulo consiste em uma API RESTful desenvolvida com o ecossistema Spring Boot, responsável pelo gerenciamento das operações de benefícios da aplicação.
A API realiza integração direta com o módulo [ejb-module](../ejb-module/README.md), responsável pelas regras de negócio relacionadas à transferência de valores entre benefícios distintos.


## 🛠️ Tecnologias e Arquitetura

- **Java 17** & **Spring Boot 3.5.14** — Core do desenvolvimento.
- **Spring Data JPA** — Camada de persistência e comunicação com o banco de dados.
- **H2 Database / PostgreSQL** — Banco de dados relacional para armazenamento das entidades.
- **Validation (Jakarta Validator)** — Annotations (`@Valid`) para garantir que as regras de negócio dos campos sejam validadas antes de chegar ao banco.
- **Service Pattern & DTOs** — Estrutura baseada em separação de responsabilidades entre camadas (Controller, Service e Repository), utilizando DTOs para transferência segura de dados entre cliente e servidor, evitando exposição direta das entidades.
- **JUnit 5** — Framework para testes unitários.
- **Mockito** — Criação de mocks para isolamento de dependências durante os testes.
- **JaCoCo** — Geração de relatórios de cobertura de código.

## 📁 Estrutura do Backend Module

```text
backend-module/
├── src/
│   ├── main/
│   │   ├── java/com/example/backend/
│   │   │   ├── config/                 # Configurações da aplicação
│   │   │   ├── controller/             # Endpoints REST
│   │   │   ├── DTO/                    # Objetos de transferência de dados
│   │   │   ├── parser/                 # Conversão e tratamento de dados
│   │   │   ├── repository/             # Acesso ao banco de dados (JPA)
│   │   │   ├── service/                # Regras de negócio
│   │   │   └── BackendApplication.java # Classe principal Spring Boot
│   │   └── resources/                  # Arquivos de configuração
│   │
│   └── test/
│       ├── java/com/example/backend/
│       │   ├── controller/
│       │   ├── converter/
│       │   ├── mock/
│       │   └── service/
│       └── resources/
│
├── target/                             # Artefatos gerados pelo Maven
├── .gitignore
├── pom.xml                             # Dependências e build Maven
└── README.md                           # Documentação do módulo
```

## 🧩 Arquitetura do Backend Module

```mermaid
flowchart LR

A[Controller] --> B[Service]
B --> C[EJB - Module]
B -->D[Repository] --> E[(PostgreSQL)]
```

## 📋 Endpoints (API REST)

A API possui os seguintes recursos mapeados:
| Método | Endpoint | Descrição | Status HTTP |
|---------|----------|----------|------------|
|`GET`|`/api/v1/beneficios/todos`|Lista benefícios disponíveis com paginação e ordenação pelo ID|200, 400, 404|
|`GET`|`/api/v1/beneficios/detalhe/{id}`|Busca detalhada de um único benefício pelo ID|200, 400, 404|
|`POST`|`/api/v1/beneficios/novo`|Cadastra um novo benefício com nome único|201, 400|
|`PUT`|`/api/v1/beneficios/editar/{id}`|Edita os atributos do Benefício (Nome, Descrição, Valor). Não atualiza nome do benefício já existente|200, 400, 404|
|`PUT`|`/api/v1/beneficios/transferir`|Realiza a transferência de valores entre benefícios existentes|200, 400, 404|
|`DELETE`|`/api/v1/beneficios/excluir/{id}`|Exclui o benefício pelo ID|200, 400, 404|

## 📝 Requisições de Endpoints

A seguir estão exemplos de corpos de requisições em formato de `JSON` utilizados pelos principais endpoints da API:

### Novo Benefício

`POST /api/v1/beneficios/novo`

```json
{
  "nome": "Vale Alimentação",
  "descricao": "Benefício destinado à alimentação dos colaboradores",
  "valor": 1500.00
}
```

### Atualizar Benefício

`PUT /api/v1/beneficios/editar/{id}`

```json
{
  "nome": "Vale Alimentação Premium",
  "descricao": "Benefício atualizado",
  "valor": 2000.00
}
```

### Transferir Benefícios

`PUT /api/v1/beneficios/transferir`

```json
{
  "fromId": 1,
  "toId": 2,
  "amount": 500.00
}
```

## 📖 Documentação da API (Swagger)

A API possui documentação interativa utilizando Swagger/OpenAPI permitindo visualizar e testar os endpoints diretamente pelo navegador:

[documentação da API através do swagger](http://localhost:8080/swagger-ui/index.html)


## ⚙️ Como Executar o Servidor

Abra o seu terminal dentro da pasta do backend-module e execute:

```bash
# Executar a aplicação Spring Boot
mvn spring-boot:run
```

O servidor será iniciado na porta **8080**. Os logs de inicialização e conexões serão exibidos diretamente no terminal.

- **PostgreSQL** — Banco de dados relacional robusto utilizado em ambiente de desenvolvimento para garantir a persistência e integridade dos dados da aplicação.
- **H2 Database** — Banco de dados em memória configurado exclusivamente para o ambiente de **Testes Unitários e de Integração**, garantindo isolamento completo e execuções de testes em alta velocidade, sem afetar os dados reais.


## 💡 Configuração de Ambiente

Para configurar a conexão com o banco de dados, as propriedades do Spring Data JPA e os recursos do Swagger/OpenAPI, consulte o arquivo:
[application.properties](../backend-module/src/main/resources/application.properties)

## 💡 Configuração de Ambiente para Testes

O ambiente de testes utiliza banco de dados H2 em memória, garantindo isolamento e execução segura dos testes  unitários e de integração sem interferir dos dados reais da aplicação.

As propriedades desse ambiente estão definidas no arquivo:

[application-test.properties](../backend-module/src/test/resources/application-test.properties)

## 📊 Cobertura dos Testes Unitários

O projeto utiliza as seguintes ferramentas para realizar os testes unitários:
 - `JUnit 5` para teste unitário
 - `Mockito` para simulação de dependências(Mocks)
 - `JaCoCo`  para geração dos relatórios de cobertura de código


### Execução dos Testes

Para executar todos os testes do módulo, utilize:

```bash
mvn test
```

### Geração o Relatório de Cobertura

Para gerar o relatório de cobertura com ``JaCoCo``:
```bash
mvn clean verify
```

Após a execução, o relatório estará disponível em:

`target/site/jacoco/index.html`


Abra o arquivo ``index.html`` em um navegador para visualizar os indicadores de cobertura por pacote, classe e método.
