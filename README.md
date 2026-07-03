# Vinil Mania — Sistema de Compra/Venda de Discos de Vinil

Sistema Web desenvolvido para a disciplina **Desenvolvimento de Software para a Web 1** (Prof. Delano M. Beder — UFSCar), utilizando **Spring MVC**, **Spring Data JPA**, **Thymeleaf** e **Spring Security**, com uma camada de **API REST** complementar.

## Tecnologias utilizadas

- Java 17
- Spring Boot 3.2.5
- Spring MVC
- Spring Data JPA / Hibernate
- Thymeleaf
- Spring Security (autenticação baseada em sessão + roles)
- Bean Validation (Hibernate Validator)
- Maven

## Banco de dados

- **SGBD utilizado:** MySQL 8
- **Nome do banco:** `vinildb`
- **Estratégia de schema:** `spring.jpa.hibernate.ddl-auto=create-drop` — as tabelas são criadas automaticamente pelo Hibernate a cada inicialização da aplicação, a partir das entidades JPA. Não há necessidade de rodar scripts SQL manuais de criação de schema.

### Como criar o banco antes de rodar a aplicação

```sql
CREATE DATABASE vinildb;
```

### Configuração de conexão (`src/main/resources/application.properties`)

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/vinildb
spring.datasource.username=root
spring.datasource.password=
```

Ajuste usuário/senha conforme sua instalação local do MySQL.

## Usuários populados na inicialização

A classe `VinilApplication` (anotada com `@SpringBootApplication`) popula automaticamente, via `CommandLineRunner`, os seguintes usuários de exemplo ao subir a aplicação:

| Papel (Role) | E-mail | Senha | Observação |
|---|---|---|---|
| ADMIN | admin@vinil.com | admin123 | Administrador do sistema, gerencia clientes e lojas |
| LOJA | contato@vinilmania.com | loja123 | Loja de exemplo: "Vinil Mania" |
| CLIENTE | pedro@email.com | cliente123 | Cliente de exemplo: "Pedro Limas" |

Todas as senhas são armazenadas com hash **BCrypt** — nunca em texto puro.

Também é criado, na inicialização, um disco de exemplo ("Abbey Road" — The Beatles) associado à loja de exemplo.

## Modelo de dados

`Usuario` é a entidade base (e-mail, senha, role), da qual `Cliente` e `Loja` **herdam** (estratégia `@Inheritance(JOINED)`). `Disco` pertence a uma `Loja`. `Proposta` relaciona um `Cliente` a um `Disco`.

## Funcionalidades (camada Web — Thymeleaf)

- R1/R2: CRUD de clientes e lojas (administrador)
- R3: Cadastro de disco para venda (loja)
- R4: Listagem pública de discos com filtro por artista/gênero
- R5: Proposta de compra (cliente)
- R6: Listagem de discos da loja
- R7: Listagem de propostas do cliente
- R8: Loja aceita/rejeita proposta, com notificação por e-mail (simulada via console)
- R9: Internacionalização (português/inglês)
- R10: Validação de campos e tratamento de erros (página de erro amigável)

## API REST

Endpoints REST disponíveis em `/api/**`, **sem necessidade de autenticação**:

### Clientes
- `POST /api/clientes`
- `GET /api/clientes`
- `GET /api/clientes/{id}`
- `PUT /api/clientes/{id}`
- `DELETE /api/clientes/{id}`

### Lojas
- `POST /api/lojas`
- `GET /api/lojas`
- `GET /api/lojas/{id}`
- `PUT /api/lojas/{id}`
- `DELETE /api/lojas/{id}`

### Discos
- `POST /api/discos/lojas/{id}` — cria disco associado à loja de id `{id}`
- `GET /api/discos/lojas/{id}` — lista discos da loja de id `{id}`
- `GET /api/discos/artistas/{nome}` — lista discos de um artista

### Propostas
- `GET /api/propostas/discos/{id}` — lista propostas de um disco
- `GET /api/propostas/clientes/{id}` — lista propostas de um cliente

## Configuracao de envio de e-mail

O sistema envia notificacoes por e-mail quando uma proposta e aceita ou rejeitada, usando SMTP via Google Mail.

Para que o envio de e-mail funcione, defina as seguintes variaveis de ambiente antes de rodar a aplicacao:

````bash
export EMAIL_USERNAME="seu-email@gmail.com"
export EMAIL_PASSWORD="sua-senha-de-app-do-gmail"
```

A senha de app e gerada em https://myaccount.google.com/apppasswords (requer verificacao em duas etapas ativada na conta Gmail).

**Caso essas variaveis nao sejam configuradas, a aplicacao continua funcionando normalmente** (graca a valores padrao de fallback no application.properties), mas qualquer tentativa de envio de e-mail real ira falhar com erro de autenticacao. Esse comportamento foi validado tambem usando o servico de teste Mailtrap (sandbox SMTP), confirmando que a logica de envio funciona corretamente.


## Como executar

```bash
# 1. Criar o banco
mysql -u root -e "CREATE DATABASE vinildb;"

# 2. Rodar a aplicação
mvn spring-boot:run
```

A aplicação sobe em `http://localhost:8080`.

- Acesso web: `http://localhost:8080/login`
- Listagem pública (sem login): `http://localhost:8080/discos`
- API REST: `http://localhost:8080/api/...`

## Projeto T8 — Cliente REST (aplicação separada)

O T8 é um projeto web **independente** que consome a API REST do T7 (este projeto).

**Repositório:** https://github.com/Pedro-Junqueira/vinil-cliente

### Como executar o T8

1. Certifique-se que o projeto principal (T7) está rodando na porta 8080
2. Clone o repositório do T8:
```bash
git clone https://github.com/Pedro-Junqueira/vinil-cliente.git
cd vinil-cliente
```
3. Rode o projeto:
```bash
mvn spring-boot:run
```
4. Acesse: http://localhost:8081/clientes

### O que o T8 implementa

- Aplicação web Spring MVC + Thymeleaf que consome a REST API do T7
- CRUD completo de Clientes via RestClient:
  - **CREATE** → cadastro de novo cliente via formulário
  - **READ** → listagem de todos os clientes
  - **UPDATE** → edição de cliente existente
  - **DELETE** → remoção de cliente
- Tratamento de erros da API (duplicidade, falhas técnicas)
- Roda na porta **8081**, consome a API na porta **8080**
