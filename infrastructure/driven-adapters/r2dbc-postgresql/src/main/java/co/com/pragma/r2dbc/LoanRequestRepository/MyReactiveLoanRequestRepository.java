package co.com.pragma.r2dbc.LoanRequestRepository;

import co.com.pragma.model.loanRequest.LoanRequest;
import co.com.pragma.r2dbc.entity.LoanRequestEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

// TODO: This file is just an example, you should delete or modify it
public interface MyReactiveLoanRequestRepository extends ReactiveCrudRepository<LoanRequestEntity, BigInteger>, ReactiveQueryByExampleExecutor<LoanRequestEntity> {
    @Query("SELECT * FROM loan_request_entity LIMIT :#{#pageable.pageSize} OFFSET :#{#pageable.offset}")
    Flux<LoanRequestEntity> findAllBy(Pageable pageable);
    Flux<LoanRequestEntity> findAllByStatus(String status, Pageable pageable);
    Mono<Long> countByStatus(String status);
}
