package co.com.pragma.api;

import co.com.pragma.api.mapper.UserDtoMapper;
import co.com.pragma.model.user.User;
import co.com.pragma.model.user.dto.UserIntDto;
import co.com.pragma.model.user.dto.userOutDto;
import co.com.pragma.usecase.user.UserUseCase;
import enums.RangeSalaryEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import co.com.pragma.api.exceptionHandler.ValidationExceptionHandler;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class Handler {

    private final UserUseCase userUseCase;
    private final UserDtoMapper userDtoMapper;
    private final Logger log;

    public Mono<ServerResponse> GETUserUseCase(ServerRequest serverRequest) {
        Flux<User> users = userUseCase.listUser();
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(users.map(userDtoMapper::toDto),userOutDto.class);

    }

    public Mono<ServerResponse> listenGETOtherUseCase(ServerRequest serverRequest) {
        // useCase2.logic();
        return ServerResponse.ok().bodyValue("");
    }

    public Mono<ServerResponse> POSTUserUseCase(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(UserIntDto.class)
                .map(userDtoMapper::toUserFromIntDto)
                .flatMap(user -> {
                    String errorValidationFields = Stream.<String>builder()
                            .add(user.getName() == null || user.getName().isEmpty() ? "name" : null)
                            .add(user.getLastName() == null || user.getLastName().isEmpty() ? "lastName" : null)
                            .add(user.getEmail() == null || user.getEmail().isEmpty() ? "email" : null)
                            .add(user.getSalary() == null ? "salary" : null)
                            .build()
                            .filter(Objects::nonNull)
                            .collect(Collectors.joining(", "));
                    if (!errorValidationFields.isEmpty()) {
                        return Mono.error(new ValidationExceptionHandler("Error the field is empty: " + errorValidationFields + "."));
                    }
                    if(user.getSalary().compareTo(RangeSalaryEnum.MAX_SALARY.getValue()) > 0 || user.getSalary().compareTo(RangeSalaryEnum.MIN_SALARY.getValue()) < 0){
                        return Mono.error(new ValidationExceptionHandler("The salary is not in the range : ($1 - $15000000) " + errorValidationFields + "."));
                    }
                    return Mono.just(user);
                })
                .flatMap(userUseCase::registerUser)
                .map(userDtoMapper::toIntDto)
                .flatMap(dto -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("User: " +dto.getName()+ " create correctly"))
                .onErrorResume(ValidationExceptionHandler.class, ex -> {
                    String errorMessage = "business error: " + ex.getMessage();
                    return ServerResponse.status(HttpStatus.CONFLICT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(errorMessage);
                });
    }

    public Mono<ServerResponse> GETUserUseCaseByEmail(ServerRequest serverRequest) {
        String email = serverRequest.pathVariable("email");
        return userUseCase.findUserByEmail(email)
                .flatMap(user -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(user))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
