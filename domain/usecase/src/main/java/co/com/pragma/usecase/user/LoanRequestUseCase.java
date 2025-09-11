package co.com.pragma.usecase.user;

import co.com.pragma.model.loanRequest.LoanRequest;
import co.com.pragma.model.loanRequest.exception.FieldValidationException;
import co.com.pragma.model.loanRequest.exception.ValidateInputDateException;
import co.com.pragma.model.loanRequest.gateways.LoanRequestRepository;
import co.com.pragma.model.pagination.PageRequest;
import co.com.pragma.model.pagination.PageResult;
import enums.LoanType;
import enums.StatusLoanRequestEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
@Log
public class LoanRequestUseCase {

    private final LoanRequestRepository loanRequestRepository;

    public Mono<PageResult<LoanRequest>> listLoanRequest(PageRequest pageRequest) {
        return loanRequestRepository.listLoanRequest(pageRequest)
                .doOnSubscribe(subscription -> log.info("Init search from users"))
                .doOnNext(result -> log.info("Page init"));
    }

    public Mono<PageResult<LoanRequest>> listLoanRequest(PageRequest pageRequest, String status) {
        return loanRequestRepository.listLoanRequest(pageRequest, status)
                .doOnSubscribe(subscription -> log.info("Searching loans with status"));
    }


    public Mono<LoanRequest> registerLoanRequest(LoanRequest loanRequest){

        return Mono.just(loanRequest)
                .filter(loan ->
                        loan.getLoan_type().equals(LoanType.PERSONAL.getValue()) ||
                                loan.getLoan_type().equals(LoanType.LIBRE.getValue()) ||
                                loan.getLoan_type().equals(LoanType.VIVIENDA.getValue()))
                .switchIfEmpty(
                        Mono.error(new FieldValidationException("Loan type not allowed")))
                .flatMap(loan -> {
                    loan.setStatus(StatusLoanRequestEnum.PENDIENTE_REVISION.getValue());
                    return Mono.just(loan);
                    })
                .flatMap(loanRequestRepository::RegisterLoanRequest)
                .doOnSubscribe(subscription -> log.info("microservice create loanRequest init"))
                .doOnNext(loan -> log.info("{} loan created correctly")).onErrorResume(throwable ->
                {
                    if(throwable instanceof FieldValidationException){
                        return Mono.error(throwable);
                    }
                    else{
                        log.info(throwable.getCause().getMessage());
                        return Mono.error(new RuntimeException("General error!"));
                    }
                });
    }
}
