# library

Sistema de gestão de biblioteca com recursos de recomendação de livros e integração com a API do Google Books.

## Sobre o Projeto

Este projeto foi desenvolvido para criar um sistema completo de gestão de biblioteca, permitindo o cadastro de livros, usuários e controle de empréstimos, com funcionalidades adicionais de recomendação de livros baseada nos gostos dos usuários.

### Principais Recursos

- **Gestão de Usuários**: Cadastro, atualização, consulta e remoção de usuários
- **Gestão de Livros**: Cadastro, atualização, consulta e remoção de livros
- **Controle de Empréstimos**: Registro de empréstimos e devoluções de livros
- **Sistema de Recomendações**: Recomendações de livros baseadas nas preferências de categoria do usuário
- **Integração com Google Books**: Busca e importação de livros diretamente da API Google Books

## Arquitetura

- **API REST**: Desenvolvida com Spring Boot
- **Banco de Dados**: PostgreSQL
- **Containerização**: Docker e Docker Compose
- **Orquestração**: Kubernetes

## Modelo de Dados

### Usuários
- ID
- Nome
- Email
- Telefone
- Data de Cadastro

### Livros
- ID
- Título
- Autor
- ISBN
- Data de Publicação
- Categoria

### Empréstimos
- ID
- ID do Usuário
- ID do Livro
- Data de Empréstimo
- Data de Devolução
- Status (PENDENTE, EMPRESTADO, DEVOLVIDO)

## API REST

A API segue o padrão RESTful e está documentada com Swagger/OpenAPI. 
Para acessar a documentação da API, visite:

- Local: http://localhost:8000/swagger-ui/index.html
- Produção: https://library.bonam.cc/swagger-ui/index.html

### Principais Endpoints

#### Usuários
- `POST /api/v1/library-user` - Criar usuário
- `GET /api/v1/library-users` - Listar todos usuários
- `GET /api/v1/library-user/{id}` - Obter usuário específico
- `PUT /api/v1/library-user/{id}` - Atualizar usuário
- `DELETE /api/v1/library-user/{id}` - Remover usuário

#### Livros
- `POST /api/v1/books` - Criar livro
- `GET /api/v1/books` - Listar todos os livros
- `GET /api/v1/books/{id}` - Obter livro específico
- `PUT /api/v1/books/{id}` - Atualizar livro
- `DELETE /api/v1/books/{id}` - Remover livro

#### Empréstimos
- `POST /api/v1/loans` - Criar empréstimo
- `PUT /api/v1/loans/{id}` - Atualizar empréstimo

#### Google Books
- `GET /api/v1/google-books/search/{title}` - Buscar livros no Google Books
- `POST /api/v1/google-books/create/{googleBookId}` - Adicionar livro do Google Books à biblioteca

#### Recomendações
- `GET /api/v1/recommendations/user/{id}` - Obter recomendações para um usuário

## Executando o Projeto

### Pré-requisitos
- Docker e Docker Compose
- Java 21+ (para desenvolvimento local)
- Gradle (para desenvolvimento local)

### Usando Docker Compose

1. Clone o repositório:
```bash
git clone https://github.com/B0nam/library.git
cd library
```

2. Execute com Docker Compose:
```bash
docker-compose up -d
```

3. Acesse:
   - API: http://localhost:8000
   - Swagger: http://localhost:8000/swagger-ui/index.html

### Em Produção

O sistema está implantado em produção e pode ser acessado através da URL:

- https://library.bonam.cc
- Documentação API (Swagger): https://library.bonam.cc/swagger-ui/index.html

## Desenvolvimento

### Estrutura do Projeto

O projeto segue uma arquitetura limpa (Clean Architecture) com as seguintes camadas:

- **API**: Controladores REST e DTOs
- **Domain**: Entidades de domínio, serviços e regras de negócio

O padrão utilizado para os controllers e services foi o de UseCases.

## Integração com a API do Google Books

Uma das principais funcionalidades do sistema é a integração com a API do Google Books, que permite buscar informações detalhadas sobre livros e adicioná-los diretamente ao acervo da biblioteca.

### Como funciona a integração

1. **Configuração**: A API do Google Books é acessada utilizando uma chave de API configurada no arquivo `application.yml`:

```yaml
google:
  books:
    api:
      key: ${GOOGLE_API_KEY:}
```

2. **Cliente HTTP**: A comunicação com a API é realizada através do WebClient do Spring WebFlux:

```java
private final WebClient webClient;

public GetGoogleBooksService(WebClient.Builder builder) {
    this.webClient = builder.baseUrl("https://www.googleapis.com/books/v1").build();
}
```

3. **Endpoints de Integração**:

   - **Busca de livros**: Permite pesquisar livros por título na API do Google Books
     ```
     GET /api/v1/google-books/search/{title}
     ```

   - **Adicionar ao acervo**: Permite adicionar um livro encontrado no Google Books ao acervo da biblioteca
     ```
     POST /api/v1/google-books/create/{googleBookId}
     ```

4. **Mapeamento de Dados**: O sistema faz o mapeamento dos dados recebidos da API do Google Books para o modelo de dados da aplicação:

```java
return Book.builder()
        .title(info.getTitle())
        .author(author)
        .isbn(isbn)
        .publishDate(parsePublishedDate(info.getPublishedDate()))
        .category(category)
        .build();
```

### Fluxo de Uso

1. O usuário pesquisa por um livro usando o título
2. O sistema faz uma requisição à API do Google Books e exibe os resultados
3. O usuário seleciona um livro para adicionar ao acervo
4. O sistema cria um novo registro no banco de dados usando as informações obtidas da API

## Sistema de Recomendação

O sistema de recomendação de livros implementado utiliza uma abordagem baseada em categorias para sugerir novos livros aos usuários com base em seu histórico de empréstimos.

### Como funciona as recomendação

1. **Coleta de dados**: O sistema identifica todos os livros que um usuário já pegou emprestado
2. **Identificação de preferências**: Extrai as categorias dos livros que o usuário já leu
3. **Filtragem de conteúdo**: Busca outros livros nas mesmas categorias que o usuário ainda não leu
4. **Recomendação**: Apresenta uma lista de livros recomendados com base nessa análise

O algoritmo está implementado no serviço `GetBooksRecommendationService` e pode ser acessado através do endpoint:
```
GET /api/v1/recommendations/user/{id}
```

### Tecnologias Utilizadas

- **Spring Boot**: Framework para o desenvolvimento da API REST
- **Spring Data JPA**: Persistência de dados
- **Hibernate Validator**: Validação de dados
- **PostgreSQL**: Banco de dados relacional
- **Liquibase**: Controle de versão do banco de dados
- **SpringDoc OpenAPI**: Documentação da API
- **Docker**: Containerização
- **Kubernetes**: Orquestração de contêineres

## Testes

O projeto contém testes unitários implementados com JUnit e Mockito.

Para executar os testes:

```bash
./gradlew test
```

## Autor

[B0nam](https://github.com/B0nam)
