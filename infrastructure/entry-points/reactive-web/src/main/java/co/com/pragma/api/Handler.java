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
        Flux<LoanRequest> loanRequest = loanRequestUseCase.listLoanRequest();
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(loanRequest.map(loanRequestDtoMapper::toDto), LoanRequestOutDto.class);

    }


    public Mono<ServerResponse> listenGETOtherUseCase(ServerRequest serverRequest) {
        return userClient.UserValidator("oscardariomelo@gmail.com")
                .flatMap(user -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(user));
    }

    public Mono<ServerResponse> POSTUserUseCase(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(LoanRequestIntDto.class)
                .map(loanRequestDtoMapper::toLoanRequestFromIntDto)
                .filter(loanRequest ->
                        loanRequest.getLoan_type().equals(LoanType.LIBRE_INVERSION.getValue()) ||
                                loanRequest.getLoan_type().equals(LoanType.VEHICULO.getValue()) ||
                                    loanRequest.getLoan_type().equals(LoanType.VIVIENDA.getValue()) ||
                                        loanRequest.getLoan_type().equals(LoanType.ESTUDIO.getValue())
                ).switchIfEmpty(Mono.error(new ValidationExceptionHandler("Tipo de préstamo no permitido")))
                .flatMap(loanRequest ->
                        userClient.UserValidator(loanRequest.getEmail())
                                .doOnSubscribe(subscription -> log.info("Init validation user with email {}", loanRequest.getEmail()))
                                .doOnNext(userDto -> log.info("The user exists {}", userDto.getName()))
                                .map(userDto -> {
                                    loanRequest.setStatus(StatusLoanRequestEnum.PENDIETE_REVISION.getValue());
                                    return loanRequest;
                                })
                                .onErrorResume(WebClientResponseException.NotFound.class, e -> {
                                    log.error("User not found with email {}", loanRequest.getEmail());
                                    return Mono.error(new ValidationExceptionHandler("User not found with email " + loanRequest.getEmail()));
                                })
                .flatMap(loanRequestUseCase::registerLoanRequest)
                .map(loanRequestDtoMapper::toIntDto)
                .flatMap(dto -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(" loan request create correctly"))
                .onErrorResume(ValidationExceptionHandler.class, ex -> {
                    String errorMessage = "business error: " + ex.getMessage();
                    return ServerResponse.status(HttpStatus.CONFLICT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(errorMessage);
                }));
    }

}
