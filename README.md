# E-commerce API: Products and Reviews Management

Este projeto é uma API RESTful desenvolvida com Spring Boot, focada no gerenciamento de produtos e suas avaliações. Ele foi construído como parte de um trabalho prático da disciplina de Desenvolvimento Web em Camadas, demonstrando a implementação de operações CRUD em entidades relacionadas.

## Funcionalidades

A API permite realizar as seguintes operações:

* **Gerenciamento de Produtos:**
    * Criar novos produtos com detalhes como nome, descrição, preço e URL de imagem.
    * Listar todos os produtos.
    * Buscar um produto específico por ID.
    * Atualizar informações de um produto existente.
    * Excluir um produto (que também exclui suas avaliações associadas devido ao relacionamento).
* **Gerenciamento de Avaliações:**
    * Criar avaliações para produtos específicos (vinculadas pelo ID do produto).
    * Listar todas as avaliações de um produto específico.
    * Buscar uma avaliação específica por ID.
    * Atualizar informações de uma avaliação existente.
    * Excluir uma avaliação.

## Tecnologias Utilizadas

* **Framework:** Spring Boot (v3.3.12 - versão utilizada no desenvolvimento)
* **Linguagem:** Java (JDK 23)
* **Gerenciador de Dependências:** Maven
* **Banco de Dados:** PostgreSQL
* **Persistência:** Spring Data JPA (com Hibernate)
* **Documentação da API:** SpringDoc OpenAPI UI (Swagger)
* **Produtividade:** Project Lombok
* **Desenvolvimento:** Spring Boot DevTools (para hot-reloading)

## Estrutura do Projeto

O projeto segue uma arquitetura em camadas, organizada nos seguintes pacotes:

* `com.otto_lehmann.ecommerceapi.model`: Contém as classes de entidade (`Product` e `Review`).
* `com.otto_lehmann.ecommerceapi.repository`: Contém as interfaces de repositório para acesso a dados (`JpaRepository`).
* `com.otto_lehmann.ecommerceapi.service`: Contém a lógica de negócio e orquestra as operações com os repositórios.
* `com.otto_lehmann.ecommerceapi.controller`: Contém os controladores REST que expõem os endpoints da API.

## Relacionamento entre Entidades

O projeto utiliza um relacionamento **Um-para-Muitos (One-to-Many)** entre `Product` e `Review`:
* Um `Product` pode ter muitas `Review`s.
* Uma `Review` pertence a um único `Product`.

Este relacionamento é mapeado no JPA utilizando as anotações `@OneToMany` (em `Product`) e `@ManyToOne` (em `Review`), com `product_id` como chave estrangeira na tabela de avaliações. A serialização JSON bidirecional é gerenciada com `@JsonManagedReference` e `@JsonBackReference` para evitar recursão infinita.

## Configuração e Execução do Projeto

### Pré-requisitos

* Java Development Kit (JDK) 17 ou superior (JDK 23 recomendado)
* Apache Maven 3.x
* PostgreSQL (instalado e rodando localmente na porta 5432)
* pgAdmin 4 (opcional, para gerenciar o banco de dados graficamente)

### Configuração do Banco de Dados

1.  Certifique-se de que o PostgreSQL está rodando.
2.  Abra o pgAdmin 4 (ou use o `psql` via terminal).
3.  Conecte-se ao seu servidor PostgreSQL (geralmente como usuário `postgres` e a senha definida durante a instalação).
4.  Crie um novo banco de dados com o nome **`e_commerce_db`**.
5.  No arquivo `src/main/resources/application.properties`, configure as credenciais do seu banco de dados:

    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/e_commerce_db
    spring.datasource.username=postgres # ou seu usuário do PostgreSQL
    spring.datasource.password=Seguranca001*
    ```

### Executando a Aplicação

1.  Abra o projeto no IntelliJ IDEA (ou sua IDE de preferência).
2.  A IDE fará o download automático das dependências Maven.
3.  Certifique-se de que o processamento de anotações (Annotation Processing) está habilitado nas configurações do IntelliJ para o Lombok (`File > Settings > Build, Execution, Deployment > Compiler > Annotation Processors`).
4.  Execute a classe principal `EcommerceApiApplication.java` (botão 'Play' verde no IntelliJ).

A aplicação será iniciada na porta 8080 (http://localhost:8080).

## Utilização da API (Documentação Interativa - Swagger UI)

A API é auto-documentada e pode ser explorada interativamente através do Swagger UI.

* Após iniciar a aplicação, acesse:
    [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

No Swagger UI, você encontrará todos os endpoints disponíveis (`/products` e `/reviews`), poderá ver seus detalhes, esquemas de requisição/resposta, e testá-los diretamente.

### Exemplos de Requisições (para referência)

#### **Criar um Produto (POST /products)**
`http://localhost:8080/products`
```json
{
  "name": "Three Wolf Moon T-Shirt",
  "description": "A quality cotton tee featuring the iconic Keyboard Cat. Perfect for meme enthusiasts, celebrating legendary internet moments.",
  "price": 19.99,
  "imageUrl": "http://localhost:8080/images/twm_original.jpg"
}
