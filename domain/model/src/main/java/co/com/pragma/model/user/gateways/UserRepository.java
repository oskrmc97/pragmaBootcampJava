package co.com.pragma.model.user.gateways;

import co.com.pragma.model.user.User;
import reactor.core.publisher.Flux;

import java.util.List;

public interface UserRepository {
    Flux<User> listUser();
}
