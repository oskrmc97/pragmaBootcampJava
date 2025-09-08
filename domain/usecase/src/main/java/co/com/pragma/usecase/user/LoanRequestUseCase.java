package co.com.pragma.usecase.user;

import co.com.pragma.model.loanRequest.LoanRequest;
import co.com.pragma.model.loanRequest.exception.FieldValidationException;
import co.com.pragma.model.loanRequest.exception.ValidateInputDateException;
import co.com.pragma.model.loanRequest.gateways.LoanRequestRepository;
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

    public Flux<LoanRequest> listLoanRequest(){
        return loanRequestRepository.listLoanRequest().doOnSubscribe(subscription -> log.info("Init search from users"))
                .doOnNext(loanRequest -> log.info("loanRequest found"));
    }

    public Mono<LoanRequest> registerLoanRequest(LoanRequest loanRequest){

        return Mono.just(loanRequest)
                .filter(loan ->
                        loan.getLoan_type().equals(LoanType.LIBRE_INVERSION.getValue()) ||
                                loan.getLoan_type().equals(LoanType.VEHICULO.getValue()) ||
                                loan.getLoan_type().equals(LoanType.VIVIENDA.getValue()) ||
                                loan.getLoan_type().equals(LoanType.ESTUDIO.getValue()))
                .switchIfEmpty(
                        Mono.error(new FieldValidationException("Loan type not allowed")))
                .flatMap(loan -> {
                    loan.setStatus(StatusLoanRequestEnum.PENDIETE_REVISION.getValue());
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
