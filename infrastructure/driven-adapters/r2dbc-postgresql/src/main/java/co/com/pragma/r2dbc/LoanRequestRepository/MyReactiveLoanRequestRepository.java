package co.com.pragma.r2dbc.LoanRequestRepository;

import co.com.pragma.r2dbc.entity.LoanRequestEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.math.BigInteger;

// TODO: This file is just an example, you should delete or modify it
public interface MyReactiveLoanRequestRepository extends ReactiveCrudRepository<LoanRequestEntity, BigInteger>, ReactiveQueryByExampleExecutor<LoanRequestEntity> {

}
