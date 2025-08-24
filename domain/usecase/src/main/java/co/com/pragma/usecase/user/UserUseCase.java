package co.com.pragma.usecase.user;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@RequiredArgsConstructor
public class UserUseCase{

    private final UserRepository userRepository;
    private final Logger log;

    public Flux<User> listUser(){
        return userRepository.listUser().doOnSubscribe(subscription -> log.info("Init search from users"))
                .doOnNext(User -> log.info("User found")).onErrorResume(
                        throwable -> {
                            log.error(throwable.getMessage(),throwable);return Flux.empty();
                        });
    }

    public Mono<User> registerUser(User user){

        String errorValidationFields = Stream.<String>builder()
                .add(user.getName() == null || user.getName().isEmpty() ? "name" : null)
                .add(user.getLastName() == null || user.getLastName().isEmpty() ? "lastName" : null)
                .add(user.getEmail() == null || user.getEmail().isEmpty() ? "email" : null)
                .add(user.getSalary() == null ? "salary" : null)
                .build()
                .filter(Objects::nonNull)
                .collect(Collectors.joining(", "));
        if (!errorValidationFields.isEmpty()) {
            return Mono.error(new RuntimeException("Error: Campo(s) faltante(s): " + errorValidationFields + "."));
        }
        else{
        return userRepository.RegisterUser(user).doOnSubscribe(subscription -> log.info("microservice create user init")).doOnNext(User -> log.info("{} User created correctly", user.getName())).onErrorResume(
                throwable -> {
                    if (throwable instanceof Exception) {
                        log.error("The email: {} is already used", user.getEmail(), throwable);
                        return Mono.error(new RuntimeException("Error: The email is already used."));
                    }
                    else {
                        log.error("Internal Error: {}", throwable.getMessage(), throwable);
                        return Mono.error(new RuntimeException("Error creating user. Please try again later.."));
                    }
                });
        }
    }
}
