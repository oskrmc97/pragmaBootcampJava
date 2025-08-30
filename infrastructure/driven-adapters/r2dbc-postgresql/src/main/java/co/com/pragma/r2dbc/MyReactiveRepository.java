package co.com.pragma.r2dbc;

import co.com.pragma.r2dbc.entity.userEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.math.BigInteger;

// TODO: This file is just an example, you should delete or modify it
public interface MyReactiveRepository extends ReactiveCrudRepository<userEntity, BigInteger>, ReactiveQueryByExampleExecutor<userEntity> {

}
