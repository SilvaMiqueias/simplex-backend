package com.example.financial.security.utils;

public @interface ValidationsLogin {
    public static final String[] ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED = {
            "/auth/users/login",
            "/auth/users/login-customer",
            "/auth/users",
            "/auth/users/mfa/verify",
            "/auth/users/create",
            "/auth/users/create-customer",
            "/cst/public/**",
            "/report/public/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/auth/test/test",
            "/api/v1/currency/**"

    };

    // Endpoints que requerem autenticação para serem acessados
    String [] ENDPOINTS_WITH_AUTHENTICATION_REQUIRED = {
            "/auth/users/get-user",
            "/auth/users/update",
            "/auth/users/update-password",
            "/auth/test/**",
            "/report/auth/**",
    };

    // Endpoints que só podem ser acessador por usuários com permissão de cliente
    public static final String [] ENDPOINTS_CUSTOMER = {
            "/auth/users/test/customer",
            "/api/v1/customer/**",
            "/api/v1/currency/**"
    };

    // Endpoints que só podem ser acessador por usuários com permissão de administrador
    public static final String [] ENDPOINTS_ADMIN = {
            "/auth/users/test/administrator",
            "/api/v1/admin/**"
    };
}
