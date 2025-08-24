package co.com.pragma.usecase.user;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


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
        return userRepository.RegisterUser(user).doOnSubscribe(subscription -> log.info("microservice create user init")).doOnNext(User -> log.info("{} User created correctly", user.getName())).onErrorResume(
                throwable -> {
                    if (throwable instanceof Exception) {
                        log.error("The email: {} is already used", user.getEmail(), throwable);
                        return Mono.error(new RuntimeException("Error: The email is already used."));
                    } else {
                        log.error("Internal Error: {}", throwable.getMessage(), throwable);
                        return Mono.error(new RuntimeException("Error creating user. Please try again later.."));
                    }
                });
    }
}
