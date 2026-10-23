package org.salesianostriana.dam.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Gestión de Recetas")
                        .version("1.0.0")
                        .description("""
                                API REST para gestionar categorías, recetas e ingredientes.

                                - Una categoría tiene muchas recetas (1:N).
                                - Una receta tiene muchos ingredientes y un ingrediente se usa en \
                                muchas recetas (M:M).
                                - Todos los errores se devuelven como ProblemDetail (RFC 7807).""")
                        .contact(new Contact().name("chema161"))
                        .license(new License().name("Uso académico")))
                .servers(List.of(new Server()
                        .url("http://localhost:8080")
                        .description("Servidor local")));
    }
}
