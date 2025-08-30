package co.com.pragma.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API Heredad Urcunina",
                version = "1.0",
                description = "Documentación de la API con Spring WebFlux y Clean Architecture"
        )
)
public class OpenApiConfig {

}