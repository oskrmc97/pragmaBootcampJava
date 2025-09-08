package co.com.pragma.api;

import co.com.pragma.model.loanRequest.LoanRequest;
import co.com.pragma.model.loanRequest.dto.LoanRequestIntDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/list/loanRequest",
                    produces = { MediaType.APPLICATION_JSON_VALUE },
                    method = RequestMethod.GET,
                    beanClass = Handler.class,
                    beanMethod = "GETULoanRequestUseCase",
                    operation = @Operation(
                            operationId = "GETULoanRequestUseCase",
                            description = "Obtiene todas las solicitudes de préstamo registradas.",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Lista obtenida exitosamente.",
                                            content = @Content(schema = @Schema(
                                                    implementation = LoanRequest.class
                                            ))
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/v1/solicitud",
                    produces = { MediaType.APPLICATION_JSON_VALUE },
                    method = RequestMethod.POST,
                    beanClass = Handler.class,
                    beanMethod = "POSTLoanRequestUseCase",
                    operation = @Operation(
                            operationId = "POSTLoanRequestUseCase",
                            description = "Crea una nueva solicitud de préstamo. "
                                    + "Requiere un token JWT en el header de Authorization.",
                            security = { @SecurityRequirement(name = "bearerAuth") },
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Solicitud de préstamo creada exitosamente.",
                                            content = @Content(schema = @Schema(
                                                    implementation = String.class
                                            ))
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "El usuario no existe en el sistema."
                                    ),
                                    @ApiResponse(
                                            responseCode = "500",
                                            description = "Error interno al procesar la solicitud."
                                    )
                            },
                            requestBody = @RequestBody(
                                    description = "Datos de entrada para la solicitud de préstamo.",
                                    content = @Content(schema = @Schema(
                                            implementation = LoanRequestIntDto.class
                                    ))
                            )
                    )
            )
    })
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(GET("/list/loanRequest"), handler::GETULoanRequestUseCase)
                .andRoute(POST("/api/v1/solicitud"), handler::POSTLoanRequestUseCase);
    }
}