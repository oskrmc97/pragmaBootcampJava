package co.com.pragma.r2dbc.LoanRequestRepository;

import co.com.pragma.model.loanRequest.LoanRequest;
import co.com.pragma.model.loanRequest.gateways.LoanRequestRepository;
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
    public Flux<LoanRequest> listLoanRequest() {
        return repository.findAll()
                .map(entity -> mapper.map(entity, LoanRequest.class));
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
