package co.com.pragma.r2dbc.LoanStatusRepository;

import co.com.pragma.model.LoanStatus.LoanStatus;
import co.com.pragma.model.LoanStatus.gateways.LoanStatusRepository;
import co.com.pragma.model.loantype.LoanType;
import co.com.pragma.r2dbc.entity.LoanStatusEntity;
import co.com.pragma.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public class MyReactiveLoanStatusRepositoryAdapter extends ReactiveAdapterOperations<LoanStatus, LoanStatusEntity, BigInteger, MyReactiveLoanStatusRepository> implements LoanStatusRepository {

    public MyReactiveLoanStatusRepositoryAdapter(MyReactiveLoanStatusRepository loanStatusRepository, ObjectMapper mapper) {
        super(loanStatusRepository, mapper, d -> mapper.map(d, LoanStatus.class/* change for domain model */));
    }

}
