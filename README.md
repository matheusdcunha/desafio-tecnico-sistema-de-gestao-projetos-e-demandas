# Sistema de Gestão de Projetos e Demandas

API REST para gerenciamento de projetos e suas respectivas tarefas, desenvolvida como desafio técnico. O sistema permite criar, listar e deletar projetos, além de gerenciar tarefas com diferentes status, prioridades e filtros.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.5.7**
  - Spring Data JPA
  - Spring Web
  - Spring Validation
- **PostgreSQL** - Banco de dados relacional
- **Flyway** - Gerenciamento de migrações do banco de dados
- **Lombok** - Redução de código boilerplate
- **MapStruct** - Mapeamento de objetos (DTOs)
- **Docker** & **Docker Compose** - Containerização
- **Maven** - Gerenciamento de dependências
- **Mockito** - Framework de testes unitários

## Funcionalidades

### Projetos
- Criar novos projetos
- Listar todos os projetos
- Deletar projetos (deleta automaticamente todas as tarefas associadas)

### Tarefas
- Criar novas tarefas vinculadas a projetos
- Listar tarefas com filtros por:
  - Status (TODO, DOING, DONE)
  - Prioridade (LOW, MEDIUM, HIGH)
  - ID do projeto
  - Nome do projeto
- Atualizar status de tarefas
- Deletar tarefas

## Pré-requisitos

- Java 17 ou superior
- Maven 3.6+
- Docker e Docker Compose (para rodar o banco de dados)

## Como Executar

### 1. Clonar o repositório

```bash
git clone https://github.com/seu-usuario/desafio-tecnico-sistema-de-gestao-projetos-e-demandas.git
cd desafio-tecnico-sistema-de-gestao-projetos-e-demandas
```

### 2. Subir o banco de dados com Docker

```bash
docker-compose up -d
```

Isso irá criar um container PostgreSQL com as seguintes configurações:
- **Host:** localhost
- **Porta:** 5432
- **Database:** gerenciamento_projeto
- **Usuário:** postgres
- **Senha:** postgres

### 3. Configurar variáveis de ambiente

Crie um arquivo `.env` dentro da pasta `api/` baseado no `.env.example`:

```bash
cd api
cp .env.example .env
```

Edite o arquivo `.env` com as configurações do banco:

```properties
DB_USER=postgres
DB_PASSWORD=postgres
DB_DATABASE=gerenciamento_projeto
DB_HOST=localhost
DB_PORT=5432
```

### 4. Compilar e executar a aplicação

```bash
# Na pasta raiz do projeto
cd api
mvn clean install
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

### 5. Executar os testes

```bash
mvn test
```

## Endpoints da API

### Projetos

#### Criar Projeto
```http
POST /projects
Content-Type: application/json

{
  "name": "Projeto de Exemplo",
  "description": "Descrição do projeto",
  "startDate": "2025-01-01",
  "endDate": "2025-12-31"
}
```

**Resposta (201 Created):**
```json
{
  "id": 1,
  "name": "Projeto de Exemplo",
  "description": "Descrição do projeto",
  "startDate": "2025-01-01",
  "endDate": "2025-12-31"
}
```

#### Listar Todos os Projetos
```http
GET /projects
```

**Resposta (200 OK):**
```json
[
  {
    "id": 1,
    "name": "Projeto de Exemplo",
    "description": "Descrição do projeto",
    "startDate": "2025-01-01",
    "endDate": "2025-12-31"
  }
]
```

#### Deletar Projeto
```http
DELETE /projects/{id}
```

**Resposta (204 No Content)**

### Tarefas

#### Criar Tarefa
```http
POST /tasks
Content-Type: application/json

{
  "title": "Implementar autenticação",
  "description": "Adicionar sistema de login com JWT",
  "status": "TODO",
  "priority": "HIGH",
  "dueDate": "2025-02-15",
  "projectId": 1
}
```

**Resposta (201 Created):**
```json
{
  "id": 1,
  "title": "Implementar autenticação",
  "description": "Adicionar sistema de login com JWT",
  "status": "TODO",
  "priority": "HIGH",
  "dueDate": "2025-02-15",
  "projectId": 1,
  "projectName": "Projeto de Exemplo"
}
```

#### Listar Tarefas com Filtros
```http
GET /tasks?status=TODO&priority=HIGH&projectId=1
```

**Parâmetros de query (todos opcionais):**
- `status`: TODO, DOING, DONE
- `priority`: LOW, MEDIUM, HIGH
- `projectId`: ID do projeto
- `projectName`: Nome do projeto

**Resposta (200 OK):**
```json
[
  {
    "id": 1,
    "title": "Implementar autenticação",
    "description": "Adicionar sistema de login com JWT",
    "status": "TODO",
    "priority": "HIGH",
    "dueDate": "2025-02-15",
    "projectId": 1,
    "projectName": "Projeto de Exemplo"
  }
]
```

#### Atualizar Status da Tarefa
```http
PUT /tasks/{id}/status
Content-Type: application/json

{
  "status": "DOING"
}
```

**Resposta (200 OK):**
```json
{
  "id": 1,
  "title": "Implementar autenticação",
  "description": "Adicionar sistema de login com JWT",
  "status": "DOING",
  "priority": "HIGH",
  "dueDate": "2025-02-15",
  "projectId": 1,
  "projectName": "Projeto de Exemplo"
}
```

#### Deletar Tarefa
```http
DELETE /tasks/{id}
```

**Resposta (204 No Content)**

## Modelo de Dados

### Projeto (Project)
| Campo | Tipo | Restrições |
|-------|------|------------|
| id | Long | Primary Key, Auto Increment |
| name | String | Obrigatório, 3-100 caracteres |
| description | Text | Opcional |
| startDate | LocalDate | Obrigatório |
| endDate | LocalDate | Opcional |

### Tarefa (Task)
| Campo | Tipo | Restrições |
|-------|------|------------|
| id | Long | Primary Key, Auto Increment |
| title | String | Obrigatório, 5-150 caracteres |
| description | Text | Opcional |
| status | Enum | Obrigatório (TODO, DOING, DONE) |
| priority | Enum | Obrigatório (LOW, MEDIUM, HIGH) |
| dueDate | LocalDate | Opcional |
| projectId | Long | Foreign Key, Obrigatório |

### Relacionamento
- Um **Projeto** pode ter várias **Tarefas** (1:N)
- Ao deletar um Projeto, todas as suas Tarefas são deletadas automaticamente (CASCADE)

## Estrutura do Projeto

```
.
├── api/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── cloud/matheusdcunha/gerenciamento_projetos/
│   │   │   │       ├── controller/       # Controllers REST
│   │   │   │       ├── domain/           # Entidades JPA
│   │   │   │       ├── dto/              # Data Transfer Objects
│   │   │   │       ├── repository/       # Repositories JPA
│   │   │   │       ├── service/          # Regras de negócio
│   │   │   │       ├── mapper/           # MapStruct mappers
│   │   │   │       ├── criteria/         # Critérios de filtro
│   │   │   │       └── infra/            # Configurações e exceções
│   │   │   └── resources/
│   │   │       ├── application.yml       # Configurações da aplicação
│   │   │       └── db/migration/         # Scripts Flyway
│   │   └── test/                         # Testes unitários
│   ├── .env.example                      # Exemplo de variáveis de ambiente
│   └── pom.xml                           # Dependências Maven
├── docker-compose.yml                    # Configuração Docker
├── LICENSE
└── README.md
```

## Tratamento de Erros

A API implementa um tratamento global de exceções que retorna respostas padronizadas:

**Exemplo de erro de validação (400 Bad Request):**
```json
{
  "timestamp": "2025-11-14T19:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "errors": {
    "name": "Project name must be between 3 and 100 characters"
  }
}
```

**Exemplo de recurso não encontrado (404 Not Found):**
```json
{
  "timestamp": "2025-11-14T19:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Project not found with id: 99"
}
```

## Testes

O projeto inclui testes unitários para as camadas de serviço:
- `ProjectServiceTest` - Testes do serviço de projetos
- `TaskServiceTest` - Testes do serviço de tarefas

Execute os testes com:
```bash
mvn test
```

## Contribuindo

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/nova-funcionalidade`)
3. Commit suas mudanças (`git commit -m 'Adiciona nova funcionalidade'`)
4. Push para a branch (`git push origin feature/nova-funcionalidade`)
5. Abra um Pull Request

## Licença

Este projeto está sob a licença especificada no arquivo [LICENSE](LICENSE).

## Autor

Desenvolvido por [Matheus Cunha](https://github.com/matheusdcunha)

Desafio técnico idealizado por [Matheus Leandro Ferreira](https://github.com/matheuslf/dev.matheuslf.desafio.inscritos)

---

**Status do Projeto:** Em desenvolvimento ativo
