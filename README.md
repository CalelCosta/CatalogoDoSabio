# Catálogo do Sábio
Este repositório contém a implementação de uma API REST para gerenciamento de livros, permitindo buscar informações sobre livros fictícios e reais. O projeto utiliza diversas tecnologias modernas para otimizar a performance e garantir a escalabilidade da aplicação.

## ✨ Tecnologias Utilizadas
* Java 17
* Spring Boot 3.x
* Maven (gerenciamento de dependências)
* Spring Data JPA (para persistência de dados)
* PostgreSQL (banco de dados relacional)
* Redis (cache para otimizar a leitura dos dados)
* Docker (para facilitar a execução em qualquer ambiente)
* JUnit 5 e Mockito (para testes unitários)

## 💻 Funcionalidades
A API oferece uma série de endpoints para interagir com o catálogo de livros:

* GET /books: Recupera todos os livros. Suporta paginação opcional para grandes volumes de dados.
* GET /books/{id}: Recupera um livro específico pelo seu ID.
* GET /books/genre?genre="": Recupera livros de um gênero específico.
* GET /books/author?author="": Recupera livros de um autor específico.

## Plano de Implementação
### Estrutura do Projeto com Clean Architecture
A aplicação será dividida em camadas que seguem os princípios de Clean Architecture para manter a separação de responsabilidades e facilitar a manutenção e a escalabilidade. As camadas principais são:

* Domain (Domínio): Contém as entidades e interfaces que definem as regras de negócio. Totalmente desacoplada de bibliotecas externas.
* Application (Aplicação): Implementa os casos de uso, lidando com a lógica da aplicação, interação com repositórios e outros serviços.
* Infrastructure (Infraestrutura): Camada onde reside a persistência de dados, cache, cliente REST e outras tecnologias específicas.
* Interface/Web (Interface de Usuário): Camada de apresentação e interação com a API. Inclui os controladores REST.

## Banco de Dados 
* PostgreSQL: Configurado no arquivo application.properties e pode ser executado via Docker.
* Redis: Implementado como cache, onde dados frequentemente acessados são armazenados para reduzir o tempo de resposta em leituras subsequentes.
<p>Para obtenção dos dados fictícios, foi utilizado o CHAT GPT-4 para simular as informações. A Aplicação realiza automaticamente a criação e inserção desses dados.<p>
<p>A configuração pode ser ajustada no arquivo application.properties:<p>

<p>spring.datasource.url=jdbc:postgresql://localhost:5432/catalogodosabio<p>
<p>spring.datasource.username=myuser<p>
<p>spring.datasource.password=secret<p>

<p>spring.redis.host=localhost<p>
<p>spring.redis.port=6379<p>

## Testes
Os testes unitários foram escritos utilizando JUnit 5 e Mockito para garantir o correto funcionamento dos serviços e casos de uso. Testes são fundamentais para assegurar que as funcionalidades estejam sempre íntegras durante a evolução do projeto.

# 🚀 Docker
Para facilitar a reprodutibilidade da solução, o projeto inclui um Dockerfile e um arquivo docker-compose.yml que configuram o ambiente com PostgreSQL e Redis. Isso permite que a aplicação seja executada em qualquer ambiente de maneira consistente.

## Instruções de Execução com Docker
1. Certifique-se de ter o [Docker](https://docs.docker.com/get-started/get-docker/) instalado.
2. Utilize uma IDEA de sua preferência: [IntelliJ](https://www.jetbrains.com/pt-br/idea/download/?section=windows)/[Eclipse](https://eclipseide.org/)
3. Clone este repositório.
5. Instale as dependências com `mvn dependency:resolve -U`
6. Execute o comando abaixo para subir os serviços:
`docker-compose up`
7. Inicie a aplicação com o modo `start/debug` ou pelo terminal com `mvn spring-boot:run`

Acesse a aplicação através dos endpoints disponíveis, como 
[`localhost:8080/api/v1/books`](http://localhost:8080//api/v1/books).

### Tips do projeto

Na aplicação foram utilizados alguns recursos interessantes que vale a pena dar uma 👀

* [MapStruct](https://mapstruct.org/)
* [Estrutura Rest](https://restfulapi.net/)
* [Clean Arch](https://medium.com/@gabrielfernandeslemos/clean-architecture-uma-abordagem-baseada-em-princ%C3%ADpios-bf9866da1f9c)
* [Redis](https://redis.io/docs/latest/develop/get-started/data-store/)

---
