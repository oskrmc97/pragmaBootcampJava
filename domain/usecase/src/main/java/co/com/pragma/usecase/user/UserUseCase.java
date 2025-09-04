package co.com.pragma.usecase.user;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.exception.EmailAlreadyInUseException;
import co.com.pragma.model.user.gateways.UserRepository;
import enums.RangeSalaryEnum;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@RequiredArgsConstructor
@Log
public class UserUseCase{

    private final UserRepository userRepository;

    public Flux<User> listUser(){
        return userRepository.listUser().doOnSubscribe(subscription -> log.info("Init search from users"))
                .doOnNext(User -> log.info("User found"));
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
                return Mono.error(new RuntimeException("Error the field is empty: " + errorValidationFields + "."));
            }
            if(user.getSalary().compareTo(RangeSalaryEnum.MAX_SALARY.getValue()) > 0 || user.getSalary().compareTo(RangeSalaryEnum.MIN_SALARY.getValue()) < 0){
                return Mono.error(new RuntimeException("The salary is not in the range : ($1 - $15000000) " + errorValidationFields + "."));
            }
            else {
                return userRepository.RegisterUser(user)
                        .doOnSubscribe(subscription -> log.info("microservice create user init"))
                        .doOnNext(User -> log.info("{} User created correctly")).
                        onErrorResume(throwable -> {
                            if (throwable.getCause().getMessage().equals("duplicate key value violates unique constraint \"users_email_key\"")) {
                                return Mono.error(new EmailAlreadyInUseException("Error creating user, the email:" + user.getEmail() + " is duplicate"));
                            } else {
                                return Mono.error(new RuntimeException("Error creating user"));
                            }
                        });
            }
    }

    public Mono<User> findUserByEmail(String email){
        return userRepository.findUserByEmail(email)
                .doOnSubscribe(subscription -> log.info("init search by email"))
                .doOnNext(user -> log.info("User found: "+user.getName())).doOnError(Throwable::printStackTrace);
    }
}
