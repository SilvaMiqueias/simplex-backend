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
            "/swagger-ui/index.html",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/**",
            "/auth/test/test"

    };

    // Endpoints que requerem autenticação para serem acessados
    String [] ENDPOINTS_WITH_AUTHENTICATION_REQUIRED = {
            "/auth/users/get-user",
            "/auth/users/update",
            "/auth/users/update-password",
            "/auth/test/**",
            "/report/auth/**",
            "/api/v1/public/**",
            "authenticated/**"
    };

    // Endpoints que só podem ser acessador por usuários com permissão de cliente
    public static final String [] ENDPOINTS_CUSTOMER = {
            "/auth/users/test/customer",
            "/api/v1/customer/**",

    };

    // Endpoints que só podem ser acessador por usuários com permissão de administrador
    public static final String [] ENDPOINTS_ADMIN = {
            "/auth/users/test/administrator",
            "/api/v1/admin/**",
    };
}
