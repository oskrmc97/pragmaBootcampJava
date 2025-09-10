package co.com.pragma.api;

import co.com.pragma.api.mapper.LoanRequestDtoMapper;
import co.com.pragma.model.loanRequest.LoanRequest;
import co.com.pragma.model.loanRequest.dto.LoanRequestIntDto;
import co.com.pragma.model.loanRequest.dto.LoanRequestOutDto;
import co.com.pragma.model.loanRequest.dto.UserDto;
import co.com.pragma.usecase.user.LoanRequestUseCase;
import enums.LoanType;
import enums.StatusLoanRequestEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import co.com.pragma.api.exceptionHandler.ValidationExceptionHandler;

@Component
@RequiredArgsConstructor
public class Handler {

    private final LoanRequestUseCase loanRequestUseCase;
    private final LoanRequestDtoMapper loanRequestDtoMapper;
    private final Logger log;

    private final UserClient userClient;


    public Mono<ServerResponse> GETULoanRequestUseCase(ServerRequest serverRequest) {
        String token = serverRequest.headers().firstHeader(HttpHeaders.AUTHORIZATION);

        return userClient.UserAdviserValidator(token)
                .doOnSubscribe(sub -> log.info("init search of adviser"))
                .doOnNext(userDto -> log.info("Adviser found: {}", userDto.getEmail()))
                .flatMap(userDto ->
                        loanRequestUseCase.listLoanRequest()
                                .collectList().map(loanRequestDtoMapper::toDtoList)
                                .flatMap(list ->
                                        ServerResponse.ok()
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .bodyValue(list)
                                )
                )
                .switchIfEmpty(
                        ServerResponse.status(HttpStatus.FORBIDDEN)
                                .bodyValue("Invalid token")
                )
                .onErrorResume(e ->
                        ServerResponse.status(HttpStatus.UNAUTHORIZED)
                                .bodyValue("User is not authorized only adviser")
                );
    }

    public Mono<ServerResponse> POSTLoanRequestUseCase(ServerRequest serverRequest) {

        String token = serverRequest.headers().firstHeader(HttpHeaders.AUTHORIZATION);

        return serverRequest.bodyToMono(LoanRequestIntDto.class)
                .map(loanRequestDtoMapper::toLoanRequestFromIntDto)
                .flatMap(loanRequest ->
                        userClient.UserValidator(loanRequest.getEmail(), token)
                                .doOnSubscribe(sub -> log.info("Init validation user with email {}", loanRequest.getEmail()))
                                .doOnNext(userDto -> log.info("The user exists {}", userDto.getName()))
                                .thenReturn(loanRequest)
                )
                .onErrorResume(WebClientResponseException.class, ex -> {
                    if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                        return Mono.error(new ValidationExceptionHandler("The user does not exist"));
                    }
                    if (ex.getStatusCode() == HttpStatus.FORBIDDEN) {
                        return Mono.error(new ValidationExceptionHandler("The user logged is not same that the user in the loan request "));
                    }
                    return Mono.error(new RuntimeException("Error calling user service: " + ex.getMessage()));
                })
                .flatMap(loanRequestUseCase::registerLoanRequest)
                .map(loanRequestDtoMapper::toIntDto)
                .flatMap(dto -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue("loan request created correctly"))
                .onErrorResume(ValidationExceptionHandler.class, ex -> {
                    String errorMessage = "business error: " + ex.getMessage();
                    return ServerResponse.status(HttpStatus.NOT_FOUND)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(errorMessage);
                })
                .onErrorResume(RuntimeException.class, ex -> {
                    String errorMessage = "business error: " + ex.getMessage();
                    return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(errorMessage);
                });
    }

}

