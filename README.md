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

---

## ⚡ Funcionalidades Implementadas

### 1. Sistema de Autenticação e Autorização

**Descrição e Objetivo**  
API de autenticação robusta com JWT e suporte a MFA (Multi-Factor Authentication) via Google Authenticator.

**Fluxo de Execução**

```
┌─────────────────────────────────────────────────────────────────────────┐
│                    PROCESSO DE AUTENTICAÇÃO                              │
├─────────────────────────────────────────────────────────────────────────┤
│  1. POST /auth/users/login-customer com {username, password}             │
│  2. UserService.authenticateUserCustomer():                              │
│     → Busca usuário no banco                                             │
│     → Valida senha com BCryptPasswordEncoder                             │
│     → Verifica role CUSTOMER                                             │
│  3. Se user.mfaEnabled == true:                                          │
│     → Gera token temporário (5 min)                                      │
│     → Retorna {token: "MFA_REQUIRED:...", requireMfa: true}              │
│  4. POST /auth/users/mfa/verify com {tempToken, code}                    │
│     → MFAService.validateTotp(secret, code)                              │
│     → Se válido, gera JWT definitivo (24h)                               │
│  5. Se MFA não habilitado:                                               │
│     → JwtTokenService.generateToken()                                    │
│     → Retorna JWT com claims (username, roles)                           │
└─────────────────────────────────────────────────────────────────────────┘
```

**Segurança Implementada**
```java
// SecurityConfig.java
.authorizeHttpRequests(auth -> auth
    .requestMatchers(ENDPOINTS_NOT_REQUIRED).permitAll()
    .requestMatchers(ENDPOINTS_ADMIN).hasRole("ADMINISTRATOR")
    .requestMatchers(ENDPOINTS_CUSTOMER).hasRole("CUSTOMER")
    .anyRequest().authenticated()
)
```

---

### 2. CRUD de Transações Financeiras

**Descrição e Objetivo**  
API REST completa para gerenciamento de transações, com suporte a categorização, tipos de transação e métodos de pagamento.

**Endpoints e Lógica**

```java
// TransactionCustomerController.java
@PostMapping("/create")
public ResponseEntity<TransactionDTO> create(@RequestBody TransactionDTO dto) {
    return ResponseEntity.ok().body(transactionService.createTransaction(dto));
}

// TransactionService.java (lógica)
public TransactionDTO createTransaction(TransactionDTO dto) {
    User user = authenticationUtil.getAuthenticatedUser();  // Extrai do JWT
    Transaction entity = transactionMapper.toEntity(dto);
    entity.setUserId(user);
    return transactionMapper.toDto(transactionRepository.save(entity));
}
```

**Categorias Suportadas**
```java
public enum CategoryEnum {
    FOOD,           // Alimentação
    TRANSPORT,      // Transporte
    HOUSING,        // Moradia
    HEALTH,         // Saúde
    EDUCATION,      // Educação
    ENTERTAINMENT,  // Entretenimento
    CLOTHING,       // Vestuário
    OTHERS          // Outros
}
```

---

### 3. Gerenciamento de Orçamentos

**Descrição e Objetivo**  
API para definição de limites de gastos mensais por categoria.

**Fluxo de Dados**

```
Request: POST /api/v1/customer/budget/create
Body: {
    "category": "FOOD",
    "amount": 1500.00,
    "description": "Orçamento alimentação janeiro",
    "dateReference": "2026-01-01T00:00:00"
}

↓ BudgetController.create()
↓ BudgetService.createBudget()
↓ BudgetMapper.toEntity()
↓ BudgetRepository.save()
↓ BudgetMapper.toDto()

Response: {
    "id": 1,
    "category": "FOOD",
    "amount": 1500.00,
    "description": "Orçamento alimentação janeiro",
    ...
}
```

---

### 4. Dashboard Analytics

**Descrição e Objetivo**  
Endpoints que fornecem dados agregados para visualização no dashboard do frontend.

**Cálculos Realizados**

```java
// DashboardService.java
public DashboardCardResultDTO getInfosToDashboardCardByUser() {
    User user = authenticationUtil.getAuthenticatedUser();
    
    // Busca transações do mês atual
    DashboardCardDTO currentMonth = transactionRepository
        .findSumByUserAndMonth(user.getId(), LocalDate.now());
    
    // Busca transações do mês anterior
    DashboardCardDTO previousMonth = transactionRepository
        .findSumByUserAndMonth(user.getId(), LocalDate.now().minusMonths(1));
    
    return new DashboardCardResultDTO(currentMonth, previousMonth);
}
```

**Resposta**
```json
{
    "currentMonth": {
        "income": 5000.00,
        "expense": 3200.00
    },
    "previousMonth": {
        "income": 4500.00,
        "expense": 3000.00
    }
}
```

---

### 5. Integração com API de Cotações

**Descrição e Objetivo**  
Consumo da API Frankfurter para obter cotações de moedas em tempo real.

**Implementação**

```java
// CurrencyService.java
@Service
public class CurrencyService {
    
    @Autowired
    private RestTemplate restTemplate;
    
    public void getRates() {
        LocalDate today = getPreviousBusinessDay(LocalDate.now());
        LocalDate yesterday = getPreviousBusinessDay(today.minusDays(1));
        
        String url = "https://api.frankfurter.dev/v1/"
            + yesterday + ".." + today + "?base=BRL";
            
        RatesDetailDTO response = restTemplate.getForObject(url, RatesDetailDTO.class);
        // Processa cotações...
    }
    
    // Ajusta para dias úteis (ignora fins de semana)
    private LocalDate getPreviousBusinessDay(LocalDate date) {
        while (date.getDayOfWeek() == DayOfWeek.SATURDAY ||
               date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            date = date.minusDays(1);
        }
        return date;
    }
}
```

---

## 🔧 Implementação e Qualidade do Código

### Padrões de Código Adotados

#### Java/Spring
- **Arquitetura em Camadas**: Controller → Service → Repository
- **DTOs**: Separação entre entidades e objetos de transferência
- **MapStruct**: Mapeamento automático Entity ↔ DTO
- **Lombok**: Redução de boilerplate (@Getter, @Setter, @Builder)

#### Nomenclatura
- **Classes**: PascalCase (`TransactionService`, `UserDTO`)
- **Métodos**: camelCase (`createTransaction`, `findById`)
- **Pacotes**: lowercase (`com.example.financial.service`)
- **Constantes**: UPPER_SNAKE_CASE (`ENDPOINTS_ADMIN`)
- **Endpoints REST**: kebab-case (`/find-all`, `/find-by-id`)

#### Organização de Pacotes
```
com.example.financial/
├── config/          # Configurações (Swagger, RestTemplate)
├── controller/      # Endpoints REST
├── dto/             # Data Transfer Objects
├── mapper/          # Conversores Entity ↔ DTO
├── model/           # Entidades JPA
│   └── enumerador/  # Enums
├── repository/      # Interfaces JPA
├── security/        # Configuração de segurança
│   └── utils/       # Utilitários de autenticação
└── service/         # Lógica de negócio
```

### Controle de Versão (Git)

#### Padrão de Commits
```
docs: adiciona documentação da API
feat: implementa endpoint de transações
fix: corrige validação de JWT
refactor: extrai lógica para service
test: adiciona testes de autenticação
```

#### Estrutura do Repositório
- Branch principal: `master`
- README.md com documentação técnica
- .gitignore configurado para Gradle/Java

---

## 🚀 Como Executar

### Usando Docker Compose (Recomendado)

1. **Pré-requisitos:**
   - [Podman](https://podman.io/) ou [Docker](https://www.docker.com/)
   - Git

2. **Suba todos os serviços:**

```bash
# Clone o repositório
git clone https://github.com/SilvaMiqueias/simplex-backend.git
cd simplex-backend

# Suba os containers (backend, banco, frontend)
podman compose up -d
# ou
docker compose up -d
```

3. **Acesse:**
   - Backend API: http://localhost:8081
   - Frontend: http://localhost:3000
   - Banco de dados: localhost:5432 (usuário: postgres)

> O arquivo `docker-compose.yml` já está pronto para uso. O frontend será servido em http://localhost:3000 e o backend em http://localhost:8081.

---

### Execução Manual (Java/Gradle)

#### Pré-requisitos

- Java 17+
- Gradle 8+
- PostgreSQL 14+
- Git

#### Configuração do Banco de Dados

```sql
-- Criar database
CREATE DATABASE financial;
```

#### Configurar application.properties

```properties
# Localização: src/main/resources/application.properties

spring.datasource.url=jdbc:postgresql://localhost:5432/financial
spring.datasource.username=postgres
spring.datasource.password=sua_senha

spring.jpa.hibernate.ddl-auto=validate
spring.flyway.enabled=true
spring.flyway.baseline-on-migrate=true

# JWT
jwt.secret=sua-chave-secreta-muito-segura-aqui
jwt.expiration=86400000

# Swagger
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
```

#### Executar Aplicação

```bash
# Clonar repositório
git clone https://github.com/SilvaMiqueias/simplex-backend.git
cd simplex-backend

# Executar com Gradle
./gradlew bootRun

# Ou gerar JAR e executar
./gradlew build
java -jar build/libs/financial-0.0.1-SNAPSHOT.jar
```

#### Verificar Funcionamento

- **API**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **Health Check**: http://localhost:8080/actuator/health

#### Testar Endpoints

```bash
# Criar usuário
curl -X POST http://localhost:8080/auth/users/create-customer \
  -H "Content-Type: application/json" \
  -d '{"username":"teste@email.com","password":"senha123","name":"Teste"}'

# Login
curl -X POST http://localhost:8080/auth/users/login-customer \
  -H "Content-Type: application/json" \
  -d '{"username":"teste@email.com","password":"senha123"}'

# Usar token retornado nas próximas requisições
curl -X GET http://localhost:8080/api/v1/customer/transaction/find-all \
  -H "Authorization: Bearer {seu_token_jwt}"
```

---

## 📚 Referências

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Security](https://spring.io/projects/spring-security)
- [Flyway Migrations](https://flywaydb.org/)
- [MapStruct](https://mapstruct.org/)
- [JWT.io](https://jwt.io/)
- [Frankfurter API](https://www.frankfurter.app/)

---

## 📄 Licença

Este projeto foi desenvolvido para fins acadêmicos como parte da disciplina de Programação para Internet do IFG.
