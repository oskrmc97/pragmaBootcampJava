package co.com.pragma.model.user.gateways;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.dto.LogInDTO;
import co.com.pragma.model.user.dto.SignUpDTO;
import co.com.pragma.model.user.dto.TokenDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UserRepository {
    Flux<User> listUser();
    Mono<User> RegisterUser(User user);
    Mono<User> findUserByEmail(String email);
    Mono<User> signUp(SignUpDTO dto);
    Mono<TokenDTO> login(LogInDTO dto);
}
