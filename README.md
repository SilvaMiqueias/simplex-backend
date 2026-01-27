# Simplex Backend - API de Finanças Pessoais

## 📋 Identificação do Projeto

| Campo | Informação |
|-------|------------|
| **Projeto** | Simplex - Gerenciador de Finanças Pessoais (Backend) |
| **Disciplina** | Programação para Internet |
| **Professor** | Diogo Oliveira Santo |
| **Instituição** | IFG - Instituto Federal de Goiás |
| **Data** | Janeiro/2026 |

### 👥 Integrantes do Grupo

- Pedro Lucas Dutra
- Natanael Ventura
- Davi Souza
- Miqueias Silva
- Hian Motta

---

## 📝 Resumo

O **Simplex Backend** é a API REST que sustenta o sistema de gerenciamento de finanças pessoais Simplex. Desenvolvida com Spring Boot 3.5 e Java 17, a aplicação oferece endpoints seguros para gerenciamento de transações financeiras, orçamentos, metas e autenticação de usuários.

### Objetivo Principal
Fornecer uma API robusta e segura para:
- Gerenciamento completo de transações (CRUD)
- Controle de orçamentos por categoria
- Definição e acompanhamento de metas financeiras
- Autenticação segura com JWT e suporte a MFA
- Dashboard com métricas financeiras

### Principais Tecnologias Utilizadas

| Componente | Tecnologia |
|------------|------------|
| **Framework** | Spring Boot 3.5.7 |
| **Linguagem** | Java 17 |
| **Segurança** | Spring Security + JWT (java-jwt 4.4.0) |
| **Banco de Dados** | PostgreSQL |
| **Migrations** | Flyway |
| **Documentação** | Swagger/OpenAPI (springdoc 2.6.0) |
| **MFA** | Google Authenticator (googleauth 1.4.0) |
| **QR Code** | ZXing 3.5.1 |
| **Mapeamento** | MapStruct 1.5.3 |
| **Utilitários** | Lombok |

---

## 📖 Introdução

### Contexto do Projeto

O Simplex Backend é a camada de serviços que sustenta toda a lógica de negócio do sistema de gerenciamento de finanças pessoais. A escolha do Spring Boot como framework principal se deve à sua robustez, segurança integrada e ampla adoção no mercado, proporcionando uma base sólida para APIs REST.

### Problema que o Sistema Busca Resolver

O backend foi projetado para:

1. **Persistência Segura**: Armazenar dados financeiros de forma segura com PostgreSQL
2. **Autenticação Robusta**: Implementar JWT com suporte a MFA (Google Authenticator)
3. **APIs RESTful**: Fornecer endpoints bem estruturados e documentados
4. **Controle de Acesso**: Gerenciar permissões diferenciadas (Admin/Customer)
5. **Integridade de Dados**: Garantir consistência através de migrations com Flyway

### Objetivos Específicos

| Objetivo | Descrição |
|----------|-----------|
| **API REST Completa** | Endpoints para CRUD de transações, orçamentos e metas |
| **Segurança Multinível** | Autenticação JWT + MFA com roles (ADMINISTRATOR, CUSTOMER) |
| **Dashboard Analytics** | Endpoints para métricas e gráficos financeiros |
| **Integração Externa** | Consumo de API de cotações de moedas (Frankfurter API) |
| **Documentação Automática** | Swagger/OpenAPI para facilitar integração |
| **Migrations** | Versionamento de schema do banco com Flyway |

---

## 🏗️ Arquitetura do Sistema

### Estrutura do Projeto Backend

```
simplex-backend/
├── src/main/java/com/example/financial/
│   ├── FinancialApplication.java          # Classe principal
│   ├── config/
│   │   ├── RestTemplateConfig.java        # Configuração para chamadas HTTP
│   │   └── SwaggerConfig.java             # Configuração do Swagger/OpenAPI
│   ├── controller/
│   │   ├── AuthController.java            # Autenticação (login, registro, MFA)
│   │   ├── TransactionCustomerController.java  # CRUD transações (cliente)
│   │   ├── TransactionAdminController.java     # CRUD transações (admin)
│   │   ├── BudgetController.java          # CRUD orçamentos
│   │   ├── GoalController.java            # CRUD metas
│   │   ├── DashboardCustomerController.java    # Dashboard cliente
│   │   └── DashboardAdminController.java       # Dashboard admin
│   ├── dto/
│   │   ├── UserDTO.java, LoginDTO.java    # DTOs de usuário
│   │   ├── TransactionDTO.java            # DTO de transação
│   │   ├── BudgetDTO.java, GoalDTO.java   # DTOs de orçamento e meta
│   │   ├── MfaDTO.java, MfaVerifyDTO.java # DTOs de autenticação MFA
│   │   └── interface_dto/                 # DTOs para dashboard
│   ├── mapper/
│   │   ├── TransactionMapper.java         # Mapper Entity ↔ DTO
│   │   ├── BudgetMapper.java
│   │   └── GoalMapper.java
│   ├── model/
│   │   ├── User.java                      # Entidade usuário
│   │   ├── Transaction.java               # Entidade transação
│   │   ├── Budget.java                    # Entidade orçamento
│   │   ├── Goal.java                      # Entidade meta
│   │   ├── Role.java                      # Entidade papel/permissão
│   │   └── enumerador/                    # Enums (CategoryEnum, TransactionType, etc.)
│   ├── repository/
│   │   ├── UserRepository.java
│   │   ├── TransactionRepository.java
│   │   ├── BudgetRepository.java
│   │   ├── GoalRepository.java
│   │   └── RoleRepository.java
│   ├── security/
│   │   ├── SecurityConfig.java            # Configuração Spring Security
│   │   ├── JwtTokenService.java           # Geração/validação JWT
│   │   ├── MFAService.java                # Serviço de autenticação MFA
│   │   ├── UserAuthenticationFilter.java  # Filtro de autenticação
│   │   ├── UserDetailsServiceImpl.java
│   │   └── utils/
│   └── service/
│       ├── UserService.java               # Lógica de usuários
│       ├── TransactionService.java        # Lógica de transações
│       ├── BudgetService.java             # Lógica de orçamentos
│       ├── GoalService.java               # Lógica de metas
│       ├── DashboardService.java          # Métricas e relatórios
│       └── CurrencyService.java           # Integração API de cotações
└── src/main/resources/
    └── db/migration/                      # Scripts Flyway
```

### Modelo de Dados (Entidades)

#### User (Usuário)
```java
@Entity
@Table(name = "users")
public class User {
    Long id;
    String username;          // Email do usuário
    String password;          // Senha (BCrypt)
    String name;              // Nome completo
    Boolean mfaEnabled;       // MFA ativo?
    String mfaSecret;         // Secret do Google Authenticator
    List<Role> roles;         // Papéis (ADMINISTRATOR, CUSTOMER)
}
```

#### Transaction (Transação)
```java
@Entity
@Table(name = "transaction")
public class Transaction {
    Long id;
    TransactionType transactionType;  // INCOME ou EXPENSE
    CategoryEnum category;            // Categoria (FOOD, TRANSPORT, etc.)
    PaymentMethod paymentMethod;      // Método de pagamento
    Boolean recurring;                // É recorrente?
    RecurrenceType recurrenceType;    // Tipo de recorrência
    BigDecimal amount;                // Valor
    String description;               // Descrição
    LocalDateTime dateTransaction;    // Data da transação
    User userId;                      // Usuário dono
    LocalDateTime createdAt, updatedAt;
}
```

#### Budget (Orçamento)
```java
@Entity
@Table(name = "budget")
public class Budget {
    Long id;
    CategoryEnum category;      // Categoria do orçamento
    BigDecimal amount;          // Valor limite
    String description;         // Descrição
    LocalDateTime dateReference;// Mês de referência
    User userId;                // Usuário dono
    LocalDateTime createdAt, updatedAt;
}
```

#### Goal (Meta)
```java
@Entity
@Table(name = "goal")
public class Goal {
    Long id;
    CategoryEnum category;      // Categoria da meta
    BigDecimal amount;          // Valor alvo
    String description;         // Descrição
    LocalDateTime dateStart;    // Data início
    LocalDateTime dateEnd;      // Data fim
    User userId;                // Usuário dono
    LocalDateTime createdAt, updatedAt;
}
```

### Endpoints da API

#### Autenticação (`/auth/users`)
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/login` | Login administrador |
| POST | `/login-customer` | Login cliente |
| POST | `/create` | Criar usuário admin |
| POST | `/create-customer` | Criar usuário cliente |
| PUT | `/update` | Atualizar usuário |
| PUT | `/update-password` | Alterar senha |
| POST | `/mfa/verify` | Verificar código MFA |

#### Transações (`/api/v1/customer/transaction`)
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/find-all` | Listar todas transações |
| GET | `/find-by-id/{id}` | Buscar por ID |
| POST | `/create` | Criar transação |
| PUT | `/update` | Atualizar transação |
| DELETE | `/delete/{id}` | Excluir transação |

#### Orçamentos (`/api/v1/customer/budget`)
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/find-all` | Listar orçamentos |
| GET | `/find-all-chart` | Dados para gráfico |
| POST | `/create` | Criar orçamento |
| PUT | `/update` | Atualizar orçamento |
| DELETE | `/delete?id=` | Excluir orçamento |

#### Metas (`/api/v1/customer/goal`)
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/find-all` | Listar metas |
| POST | `/create` | Criar meta |
| PUT | `/update` | Atualizar meta |
| DELETE | `/delete?id=` | Excluir meta |

#### Dashboard (`/api/v1/customer/dashboard`)
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/infos-cards` | Dados dos cards (receitas, despesas, saldo) |
| GET | `/infos-charts` | Dados para gráficos |
