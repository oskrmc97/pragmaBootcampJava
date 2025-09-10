package co.com.pragma.r2dbc.LoanTypeRepository;

import co.com.pragma.r2dbc.entity.LoanRequestEntity;
import co.com.pragma.r2dbc.entity.LoanTypeEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

// TODO: This file is just an example, you should delete or modify it
public interface MyReactiveLoanTypeRepository extends ReactiveCrudRepository<LoanTypeEntity, BigInteger>, ReactiveQueryByExampleExecutor<LoanTypeEntity> {
    @Query("SELECT * FROM loan_type WHERE name = :name")
    Mono<LoanTypeEntity> findByName(@Param("name") String name);
}
