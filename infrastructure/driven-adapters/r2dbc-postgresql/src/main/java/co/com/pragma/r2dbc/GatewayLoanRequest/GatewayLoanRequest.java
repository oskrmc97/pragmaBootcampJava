package co.com.pragma.r2dbc.GatewayLoanRequest;

import co.com.pragma.model.loanRequest.LoanRequest;
import co.com.pragma.model.loanRequest.gateways.LoanRequestRepository;
import co.com.pragma.r2dbc.entity.LoanRequestEntity;
import reactor.core.publisher.Flux;
import org.springframework.data.domain.Pageable;

public interface GatewayLoanRequest{
    Flux<LoanRequest> listLoanRequest(Pageable pageable);
}
