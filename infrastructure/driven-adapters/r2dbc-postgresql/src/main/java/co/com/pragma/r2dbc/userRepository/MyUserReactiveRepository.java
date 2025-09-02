package co.com.pragma.r2dbc.userRepository;

import co.com.pragma.model.user.User;
import co.com.pragma.r2dbc.entity.userEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

// TODO: This file is just an example, you should delete or modify it
public interface MyUserReactiveRepository extends ReactiveCrudRepository<userEntity, BigInteger>, ReactiveQueryByExampleExecutor<userEntity> {

    @Query("SELECT * FROM users WHERE email = :email")
    Mono<userEntity> findByEmail(@Param("email") String email);


}
