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
