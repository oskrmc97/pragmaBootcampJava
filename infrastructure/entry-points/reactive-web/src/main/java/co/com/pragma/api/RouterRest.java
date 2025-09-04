package co.com.pragma.api;

import co.com.pragma.model.user.User;
import io.swagger.v3.oas.annotations.Operation;
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
    @RouterOperations({ @RouterOperation(path = "/userUsecase", produces = {
            MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.GET, beanClass = Handler.class, beanMethod = "GETUserUseCase",

            operation = @Operation(operationId = "GETUserUseCase", responses = {
                    @ApiResponse(responseCode = "200", description = "get all users successfully.", content = @Content(schema = @Schema(implementation = User.class))) })),
            @RouterOperation(path = "/api/v1/usuarios/email/{email}", produces = {
                    MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.GET, beanClass = Handler.class, beanMethod = "GETUserUseCaseByEmail", operation = @Operation(operationId = "GETUserUseCaseByEmail", responses = {
                    @ApiResponse(responseCode = "200", description = "get user successfully.", content = @Content(schema = @Schema(implementation = User.class))),
                    @ApiResponse(responseCode = "404", description = "user not found by email.") }, parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "email") })),

            @RouterOperation(
                    path = "/api/v1/usuarios",
                    produces = {
                            MediaType.APPLICATION_JSON_VALUE
                    },
                    method = RequestMethod.POST,
                    beanClass = Handler.class,
                    beanMethod = "POSTUserUseCase",
                    operation = @Operation(
                            operationId = "/api/v1/usuarios",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "successful operation",
                                            content = @Content(schema = @Schema(
                                                    implementation = String.class
                                            ))
                                    )
                            },
                            requestBody = @RequestBody(
                                    content = @Content(schema = @Schema(
                                            implementation = Handler.class
                                    ))
                            )

                    )


            )


    })
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(GET("/userUsecase"), handler::GETUserUseCase)
                .andRoute(POST("/api/v1/usuarios"), handler::POSTUserUseCase)
                .andRoute(GET("/api/v1/usuarios/email/{email}"), handler::GETUserUseCaseByEmail);
    }
}
