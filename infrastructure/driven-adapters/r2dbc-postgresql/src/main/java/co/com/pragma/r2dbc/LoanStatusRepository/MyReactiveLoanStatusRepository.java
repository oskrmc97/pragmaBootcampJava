package co.com.pragma.r2dbc.LoanStatusRepository;

import co.com.pragma.r2dbc.entity.LoanStatusEntity;
import co.com.pragma.r2dbc.entity.LoanTypeEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

// TODO: This file is just an example, you should delete or modify it
public interface MyReactiveLoanStatusRepository extends ReactiveCrudRepository<LoanStatusEntity, BigInteger>, ReactiveQueryByExampleExecutor<LoanStatusEntity> {
}

