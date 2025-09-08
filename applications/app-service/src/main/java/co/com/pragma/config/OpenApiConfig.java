package co.com.pragma.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Loan Request API",
                version = "1.0",
                description = "API para gestionar la autenticación del sistema . "
                        + "Requiere autenticación con token JWT en la cabecera Authorization.",
                contact = @Contact(
                        name = "Equipo de Soporte de pragma",
                        email = "pragma@crediya.com"
                )
        )
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        description = "Autenticación JWT"
)
public class OpenApiConfig {

}