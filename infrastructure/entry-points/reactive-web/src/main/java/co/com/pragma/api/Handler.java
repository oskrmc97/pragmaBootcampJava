package co.com.pragma.api;

import co.com.pragma.api.mapper.UserDtoMapper;
import co.com.pragma.model.user.User;
import co.com.pragma.model.user.dto.UserIntDto;
import co.com.pragma.model.user.dto.userOutDto;
import co.com.pragma.model.user.exception.EmailAlreadyInUseException;
import co.com.pragma.usecase.user.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;

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
                .flatMap(userUseCase::registerUser)
                .map(userDtoMapper::toIntDto)
                .flatMap(dto -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("User: " +dto.getName()+ " create correctly")).onErrorResume(
                throwable -> {
                    if( throwable instanceof EmailAlreadyInUseException){
                        log.error("The email is already used",throwable);
                        return Mono.error(new EmailAlreadyInUseException("Error creating user email is duplicate"));
                    }
                    else{
                        log.error("Internal Error: {}", throwable.getMessage(), throwable);
                        return Mono.error(new RuntimeException("Error creating user"));
                    }
                });
    }
}
