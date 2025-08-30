package co.com.pragma.r2dbc;

import co.com.pragma.model.loanRequest.LoanRequest;
import co.com.pragma.model.loanRequest.gateways.LoanRequestRepository;
import co.com.pragma.r2dbc.entity.LoanRequestEntity;
import co.com.pragma.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@Repository
public class MyReactiveRepositoryAdapter extends ReactiveAdapterOperations<LoanRequest, LoanRequestEntity, BigInteger, MyReactiveRepository> implements LoanRequestRepository {
    public MyReactiveRepositoryAdapter(MyReactiveRepository repository, ObjectMapper mapper) {

        super(repository, mapper, d -> mapper.map(d, LoanRequest.class/* change for domain model */));
    }

    @Override
    public Flux<LoanRequest> listLoanRequest() {
        return repository.findAll()
                .map(entity -> mapper.map(entity, LoanRequest.class));
    }

    @Override
    @Transactional
    public Mono<LoanRequest> RegisterLoanRequest(LoanRequest user) {
        LoanRequestEntity LoanRequestEntity = mapper.map(user, LoanRequestEntity.class);
        return repository.save(LoanRequestEntity).map(entity -> mapper.map(entity, LoanRequest.class));
    }

}
