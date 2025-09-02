package co.com.pragma.model.user.gateways;

import co.com.pragma.model.user.RolUser;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public interface RolRepository {
    Mono<RolUser> findRolByName(String name);
    Mono<RolUser> findRolById(Integer id);
}
