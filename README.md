# Trending Products API

Projeto de portfólio: back end REST em **Quarkus (Java 21)** simulando um mini
"marketplace" — busca de produtos mais procurados e publicação de novos produtos —
com autenticação JWT. Construído para demonstrar, na prática, os requisitos comuns
de uma vaga júnior de back end (Java, framework moderno, JWT, SOLID, testes, REST,
banco relacional) e, na segunda etapa, uma automação Selenium sobre esse sistema.

## Stack

- **Java 21** + **Quarkus 3.39.1**
- **Hibernate ORM with Panache** (persistência)
- **MySQL** (banco relacional)
- **SmallRye JWT** (autenticação/autorização)
- **Kotlin** (módulo utilitário, interoperando com o código Java)
- **JUnit 5** + **RestAssured** (testes unitários e de integração)
- **Micrometer + Prometheus + Grafana** (observabilidade — requests/sec, latência, erros 4xx/5xx, JVM, CPU)

## Por que Quarkus?

Framework "cloud-native", com startup rápido e baixo consumo de memória — pensado
pra containers/Kubernetes. É uma das opções pedidas na vaga-alvo (junto com
Micronaut), então usá-lo aqui é intencional pra portfólio.

## Estrutura do projeto

```
src/main/java/com/praticando/backend/
├── auth/       -> User (entidade), AuthResource, JwtService (login/registro/token)
├── product/    -> Product (entidade), ProductResource, ProductService, ProductRepository
└── util/ (kotlin) -> utilitários em Kotlin
```

- **Camadas**: Controller (`*Resource`) → Service (interface + impl) → Repository → Banco.
  Cada camada tem responsabilidade única (Single Responsibility) e depende de
  **interfaces**, não de implementações concretas (Dependency Inversion) — o "S" e
  o "D" do SOLID na prática.

## Decisões técnicas registradas

| Decisão | Motivo |
|---|---|
| Senha com hash BCrypt (nunca texto puro) | BCrypt é lento de propósito, dificulta força bruta se o banco vazar |
| `username` com `unique = true` no banco | Constraint no banco protege contra corrida de cadastros simultâneos |
| Repository separado (não só Active Record do Panache) | Isola acesso a dados, facilita trocar estratégia de persistência e testar com mocks |

## Roadmap / progresso

- [x] Setup do projeto Quarkus (Java 21, extensões REST/Panache/MySQL/JWT/Kotlin)
- [ ] Entidade `User` + hash de senha (BCrypt)
- [ ] `AuthResource` — registro e login emitindo JWT
- [x] Observabilidade — Micrometer + Prometheus + Grafana via Docker Compose (métricas HTTP, JVM, CPU, dashboard)
- [ ] Entidade `Product` + `search_count` (ranking de mais procurados)
- [ ] `ProductResource` — `/products/top`, `/products/search`, `POST /products` (protegido)
- [ ] Endpoint assíncrono com Virtual Threads
- [ ] Testes unitários (Service) e de integração (Resource)
- [ ] Docker Compose (MySQL real + backend)
- [ ] Frontend simples (busca + publicação)
- [ ] Automação Selenium + evidência em Postgres
- [ ] Pipeline Jenkins

---

## Referência técnica do Quarkus (gerado automaticamente)

This project uses Quarkus, the Supersonic Subatomic Java Framework.
If you want to learn more about Quarkus, please visit its website: https://quarkus.io/.

### Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```
./mvnw quarkus:dev
```

NOTE: Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

### Packaging and running the application

The application can be packaged using:

```
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory. Be aware that it's not an über-jar as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an über-jar, execute the following command:

```
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an über-jar, is now runnable using `java -jar target/*-runner.jar`.

### Creating a native executable

You can create a native executable using:

```
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/trending-products-api-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

### Related Guides

- REST resources for Hibernate ORM with Panache ([guide](https://quarkus.io/guides/rest-data-panache)): Generate Jakarta REST resources for your Hibernate Panache entities and repositories
- Hibernate Validator ([guide](https://quarkus.io/guides/validation)): Bean validation using Hibernate Validator and Jakarta Validation annotations
- REST Jackson ([guide](https://quarkus.io/guides/rest#json-serialisation)): Jackson serialization support for Quarkus REST. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it
- Kotlin ([guide](https://quarkus.io/guides/kotlin)): Write your services in Kotlin
- RESTEasy Classic ([guide](https://quarkus.io/guides/resteasy)): REST endpoint framework implementing Jakarta REST and more
- SmallRye JWT ([guide](https://quarkus.io/guides/security-jwt)): Secure your applications with JSON Web Token
- JDBC Driver - MySQL ([guide](https://quarkus.io/guides/datasource)): Connect to the MySQL database via JDBC
- SmallRye JWT Build ([guide](https://quarkus.io/guides/security-jwt-build)): Create JSON Web Token with SmallRye JWT Build API

### Provided Code

**REST**

Easily start your REST Web Services.
[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)

**RESTEasy JAX-RS**

Easily start your RESTful Web Services.
[Related guide section...](https://quarkus.io/guides/getting-started#the-jax-rs-resources)