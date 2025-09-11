package co.com.pragma.model.loanRequest.gateways;

import co.com.pragma.model.loanRequest.LoanRequest;
import co.com.pragma.model.pagination.PageRequest;
import co.com.pragma.model.pagination.PageResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface LoanRequestRepository {
    Mono<PageResult<LoanRequest>> listLoanRequest(PageRequest pageRequest);
    Mono<PageResult<LoanRequest>> listLoanRequest(PageRequest pageRequest, String status);
    Mono<LoanRequest> RegisterLoanRequest(LoanRequest loanReques);
}
