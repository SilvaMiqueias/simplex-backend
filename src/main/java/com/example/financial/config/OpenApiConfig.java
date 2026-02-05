package com.example.financial.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Simplex API Financeira",
                version = "1.0.0",
                description = "API para gerenciamento financeiro",
                contact = @Contact(
                        name = "Suporte",
                        email = "simplex@gmail.com",
                        url = "http://simplex.com"
                ),
                license = @License(
                        name = "MIT",
                        url = "https://opensource.org/licenses/MIT"
                )
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Servidor Local")
        }
)
public class OpenApiConfig {
}
