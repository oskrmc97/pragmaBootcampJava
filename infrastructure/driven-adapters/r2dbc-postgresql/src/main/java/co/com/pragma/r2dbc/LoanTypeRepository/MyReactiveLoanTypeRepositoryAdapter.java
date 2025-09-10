package co.com.pragma.r2dbc.LoanTypeRepository;

import co.com.pragma.model.loantype.LoanType;
import co.com.pragma.model.loantype.gateways.LoanTypeRepository;
import co.com.pragma.r2dbc.entity.LoanTypeEntity;
import co.com.pragma.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@Repository
public class MyReactiveLoanTypeRepositoryAdapter extends ReactiveAdapterOperations<LoanType, LoanTypeEntity, BigInteger, MyReactiveLoanTypeRepository> implements LoanTypeRepository {

    public MyReactiveLoanTypeRepositoryAdapter(MyReactiveLoanTypeRepository loanTypeRepository, ObjectMapper mapper) {
        super(loanTypeRepository, mapper, d -> mapper.map(d, LoanType.class/* change for domain model */));
    }

}
