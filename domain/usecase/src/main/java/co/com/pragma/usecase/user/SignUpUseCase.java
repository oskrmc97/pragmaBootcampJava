package co.com.pragma.usecase.user;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.dto.SignUpDTO;
import co.com.pragma.model.user.gateways.UserRepository;
import reactor.core.publisher.Mono;

public class SignUpUseCase {

    private final UserRepository userRepository;

    public SignUpUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<User> signUp(SignUpDTO dto) {
        return userRepository.signUp(dto);
    }
}
