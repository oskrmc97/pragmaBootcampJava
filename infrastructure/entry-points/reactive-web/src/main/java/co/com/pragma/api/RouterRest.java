package co.com.pragma.api;

import co.com.pragma.api.dto.SwaggerErrorResponse;
import co.com.pragma.model.user.User;
import co.com.pragma.model.user.dto.LogInDTO;
import co.com.pragma.model.user.dto.TokenDTO;
import co.com.pragma.usecase.user.LogInUseCase;
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
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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
                    path = "/auth/login",
                    produces = { MediaType.APPLICATION_JSON_VALUE },
                    method = RequestMethod.POST,
                    beanClass = Handler.class,
                    beanMethod = "logIn",
                    operation = @Operation(
                            operationId = "logIn",
                            description = "Permite el logueo de usuarios registrados al sistema ",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Logueo correcto",
                                            content = @Content(schema = @Schema(
                                                    implementation = TokenDTO.class
                                            ))
                                    ),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "bad credentials.",
                                            content = @Content(schema = @Schema(
                                                    implementation = SwaggerErrorResponse.class
                                            ))
                                    ),
                                    @ApiResponse(
                                            responseCode = "500",
                                            description = "Error interno al procesar la solicitud.",
                                            content = @Content(schema = @Schema(
                                                    implementation = SwaggerErrorResponse.class
                                            ))
                                    )
                            },
                            requestBody = @RequestBody(
                                    description = "Datos de entrada para la solicitud de préstamo.",
                                    content = @Content(schema = @Schema(
                                            implementation = LogInDTO.class
                                    ))
                            )
                    )
            )
    })
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(GET("/userUsecase"), handler::GETUserUseCase)
                .andRoute(POST("/api/v1/usuarios"), handler::POSTUserUseCase)
                .andRoute(GET("/api/v1/usuarios/email/{email}"), handler::GETUserUseCaseByEmail)
                .andRoute(GET("/api/v1/adviser"), handler::GETUserRolAdviser)
                .andRoute(POST("/auth/login"), handler::logIn);
    }
}
