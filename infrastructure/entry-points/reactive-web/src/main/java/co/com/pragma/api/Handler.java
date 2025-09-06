package co.com.pragma.api;

import co.com.pragma.api.mapper.UserDtoMapper;
import co.com.pragma.model.user.User;
import co.com.pragma.model.user.dto.UserIntDto;
import co.com.pragma.model.user.dto.userOutDto;
import co.com.pragma.model.user.exception.EmailAlreadyInUseException;
import co.com.pragma.model.user.gateways.RolRepository;
import co.com.pragma.usecase.user.LogInUseCase;
import co.com.pragma.usecase.user.SignUpUseCase;
import co.com.pragma.usecase.user.UserUseCase;
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

@Component
@RequiredArgsConstructor
public class Handler {

    private final UserUseCase userUseCase;
    private final RolRepository rolRepository;
    private final SignUpUseCase signUpUseCase;
    private final LogInUseCase logInUseCase;
    private final UserDtoMapper userDtoMapper;
    private final Logger log;


    public Mono<ServerResponse> GETUserUseCase(ServerRequest serverRequest) {
        Flux<User> users = userUseCase.listUser();
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(users.map(userDtoMapper::toDto), userOutDto.class);
    }

    public Mono<ServerResponse> listenGETOtherUseCase(ServerRequest serverRequest) {
        // useCase2.logic();
        return ServerResponse.ok().bodyValue("");
    }

    public Mono<ServerResponse> POSTUserUseCase(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(UserIntDto.class)
                .map(userDtoMapper::toUserFromIntDto)
                .flatMap(userUseCase::registerUser).onErrorResume(throwable -> {
                    if(throwable.getMessage().contains("Error the field is empty:") & throwable instanceof RuntimeException){
                        return Mono.error(new ValidationExceptionHandler(throwable.getMessage()));
                    }
                    if(throwable.getMessage().contains("The salary is not in the range")){
                        return Mono.error(new ValidationExceptionHandler(throwable.getMessage()));
                    }
                    if(throwable instanceof EmailAlreadyInUseException){
                        return Mono.error(throwable);
                    }
                    return Mono.error(new RuntimeException("Error creating user"));
                })
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

    public Mono<ServerResponse> hello(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(Mono.just("Hello"), String.class);
    }
}
