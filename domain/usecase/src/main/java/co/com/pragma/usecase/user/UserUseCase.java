package co.com.pragma.usecase.user;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.gateways.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.Optional;

@RequiredArgsConstructor
public class UserUseCase{

    private final UserRepository userRepository;

    public Flux<User> listUser(){
        return userRepository.listUser();
    }
}
