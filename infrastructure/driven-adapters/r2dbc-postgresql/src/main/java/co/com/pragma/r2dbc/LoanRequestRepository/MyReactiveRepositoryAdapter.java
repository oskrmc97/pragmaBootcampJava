package co.com.pragma.r2dbc.LoanRequestRepository;

import co.com.pragma.model.loanRequest.LoanRequest;
import co.com.pragma.model.loanRequest.gateways.LoanRequestRepository;
import co.com.pragma.model.pagination.PageRequest;
import co.com.pragma.model.pagination.PageResult;
import co.com.pragma.r2dbc.LoanStatusRepository.MyReactiveLoanStatusRepository;
import co.com.pragma.r2dbc.LoanTypeRepository.MyReactiveLoanTypeRepository;
import co.com.pragma.r2dbc.entity.LoanRequestEntity;
import co.com.pragma.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@Repository
public class MyReactiveRepositoryAdapter extends ReactiveAdapterOperations<LoanRequest, LoanRequestEntity, BigInteger, MyReactiveLoanRequestRepository> implements LoanRequestRepository {

    MyReactiveLoanTypeRepository loanTypeRepository;
    MyReactiveLoanStatusRepository loanStatusRepository;

    public MyReactiveRepositoryAdapter(MyReactiveLoanRequestRepository repository, MyReactiveLoanTypeRepository loanTypeRepository, MyReactiveLoanStatusRepository loanStatusRepository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, LoanRequest.class/* change for domain model */));
        this.loanTypeRepository = loanTypeRepository;
        this.loanStatusRepository = loanStatusRepository;
    }

    @Override
    public Mono<PageResult<LoanRequest>> listLoanRequest(PageRequest pageRequest) {
        var pageable = org.springframework.data.domain.PageRequest.of(
                pageRequest.getPage(),
                pageRequest.getSize()
        );
        return repository.findAllBy(pageable)
                .flatMap(entity -> Mono.zip(
                        Mono.just(entity),
                        loanTypeRepository.findById(entity.getLoan_type()),
                        loanStatusRepository.findById(entity.getStatus())
                ))
                .map(tuple -> LoanMapper.toDomain(tuple.getT1(), tuple.getT2(), tuple.getT3()))
                .collectList()
                .zipWith(repository.count()) // total de registros
                .map(tuple -> new PageResult<>(
                        tuple.getT1(),        // lista de LoanRequest
                        tuple.getT2(),        // total
                        pageRequest.getPage(),
                        pageRequest.getSize()
                ));
    }

    @Override
    public Mono<PageResult<LoanRequest>> listLoanRequest(PageRequest pageRequest, String status) {
        var pageable = org.springframework.data.domain.PageRequest.of(pageRequest.getPage(), pageRequest.getSize());

        Flux<LoanRequestEntity> entityFlux;
        Mono<Long> countMono;

        if (status != null && !status.isBlank()) {
            entityFlux = repository.findAllByStatus(status, pageable);
            countMono = repository.countByStatus(status);
        } else {
            entityFlux = repository.findAllBy(pageable);
            countMono = repository.count();
        }

        return entityFlux
                .flatMap(entity ->
                        Mono.zip(
                                Mono.just(entity),
                                loanTypeRepository.findById(entity.getLoan_type()),
                                loanStatusRepository.findById(entity.getStatus())
                        )
                )
                .map(tuple -> LoanMapper.toDomain(tuple.getT1(), tuple.getT2(), tuple.getT3()))
                .collectList()
                .zipWith(countMono)
                .map(tuple -> new PageResult<>(
                        tuple.getT1(),
                        tuple.getT2(),
                        pageRequest.getPage(),
                        pageRequest.getSize()
                ));
    }

    @Override
    @Transactional
    public Mono<LoanRequest> RegisterLoanRequest(LoanRequest loanRequest) {
        LoanRequestEntity LoanRequestEntity = mapper.map(loanRequest, LoanRequestEntity.class);
        return repository.save(LoanRequestEntity)
                .flatMap(savedEntity ->
                        Mono.zip(
                                        loanTypeRepository.findById(savedEntity.getLoan_type()),
                                        loanStatusRepository.findById(savedEntity.getStatus())
                                )
                                .map(tuple -> LoanMapper.toDomain(savedEntity, tuple.getT1(), tuple.getT2()))
                );
    }

}
