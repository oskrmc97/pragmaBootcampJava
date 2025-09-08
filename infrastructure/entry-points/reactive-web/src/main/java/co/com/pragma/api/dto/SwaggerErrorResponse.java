package co.com.pragma.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Modelo de error")
public class SwaggerErrorResponse {
    @Schema(description = "Mensaje de error detallado", example = "bad credentials")
    private String message;

    @Schema(description = "Ruta donde ocurrió el error", example = "/auth/login")
    private String path;

    @Schema(description = "Código o tipo de error", example = "Bad Request.")
    private String error;
}
