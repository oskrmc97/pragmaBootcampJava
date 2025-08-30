package co.com.pragma.model.loanRequest.gateways;

import co.com.pragma.model.loanRequest.LoanRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LoanRequestRepository {
    Flux<LoanRequest> listLoanRequest();
    Mono<LoanRequest> RegisterLoanRequest(LoanRequest loanReques);
}
