package co.com.pragma.usecase.user;

import co.com.pragma.model.loanRequest.LoanRequest;
import co.com.pragma.model.loanRequest.exception.EmailAlreadyInUseException;
import co.com.pragma.model.loanRequest.gateways.LoanRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
@Log
public class LoanRequestUseCase {

    private final LoanRequestRepository loanRequestRepository;

    public Flux<LoanRequest> listLoanRequest(){
        return loanRequestRepository.listLoanRequest().doOnSubscribe(subscription -> log.info("Init search from users"))
                .doOnNext(loanRequest -> log.info("loanRequest found"));
    }

    public Mono<LoanRequest> registerLoanRequest(LoanRequest loanRequest){

        return loanRequestRepository.RegisterLoanRequest(loanRequest)
                .doOnSubscribe(subscription -> log.info("microservice create loanRequest init"))
                .doOnNext(User -> log.info("{} User created correctly")).onErrorResume(throwable -> {
                    if( throwable.getCause().getMessage().equals("duplicate key value violates unique constraint \"user_entity_email_key\"")){
                        return Mono.error(new EmailAlreadyInUseException("Error creating loanRequest, request is in process"));
                    }
                    else{
                        return Mono.error(new RuntimeException("Error creating loanRequest"));
                    }});

    }
}
