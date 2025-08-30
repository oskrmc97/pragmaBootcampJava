package co.com.pragma.api;

import co.com.pragma.model.loanRequest.LoanRequest;
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
    @RouterOperations({ @RouterOperation(path = "/get/students", produces = {
            MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.GET, beanClass = Handler.class, beanMethod = "getAllStudent",

            operation = @Operation(operationId = "getAllStudent", responses = {
                    @ApiResponse(responseCode = "200", description = "get all student successfully.", content = @Content(schema = @Schema(implementation = LoanRequest.class))) })),
            @RouterOperation(path = "/get/students/{student_id}", produces = {
                    MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.GET, beanClass = Handler.class, beanMethod = "findById", operation = @Operation(operationId = "findById", responses = {
                    @ApiResponse(responseCode = "200", description = "get student successfully.", content = @Content(schema = @Schema(implementation = LoanRequest.class))),
                    @ApiResponse(responseCode = "404", description = "student not found by given id.") }, parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "student_id") })),

            @RouterOperation(
                    path = "/add/student",
                    produces = {
                            MediaType.APPLICATION_JSON_VALUE
                    },
                    method = RequestMethod.POST,
                    beanClass = Handler.class,
                    beanMethod = "addStudent",
                    operation = @Operation(
                            operationId = "addStudent",
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
        return route(GET("/list/loanRequest"), handler::GETULoanRequestUseCase)
                .andRoute(POST("/api/v1/solicitud"), handler::POSTUserUseCase)
                .and(route(GET("/api/otherusercase/path"), handler::listenGETOtherUseCase));
    }
}
