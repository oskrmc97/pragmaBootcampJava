package co.com.pragma.usecase.user;

import co.com.pragma.model.loanRequest.LoanRequest;
import co.com.pragma.model.loanRequest.gateways.LoanRequestRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoanRequestUseCaseTest {

    @Mock
    private LoanRequestRepository loanRequestRepository;

    @InjectMocks
    private LoanRequestUseCase loanRequestUseCase;

    //Validar generacion de prestamo correctamente
    @Test
    public void shouldGenerateLoanRequestCorrectly(){
        Integer term = 24;
        BigDecimal amount = new BigDecimal(400);
        LoanRequest loanRequest = new LoanRequest("875309294","coco@gmail.com",amount,term,"Libre Inversión",null);

        when(loanRequestRepository.RegisterLoanRequest(any(LoanRequest.class)))
                .thenReturn(Mono.just(loanRequest));

        StepVerifier.create(loanRequestUseCase.registerLoanRequest(loanRequest))
                .expectNext(loanRequest)
                .verifyComplete();
    }
    //Validar control por tipo de prestamos no permitido
    @Test
    public void whenShouldGenerateLoanRequestThenLoanTypeNotAllowed(){
        Integer term = 24;
        BigDecimal amount = new BigDecimal(400);
        LoanRequest loanRequest = new LoanRequest("875309294","coco@gmail.com",amount,term,"Inversión",null);

        StepVerifier.create(loanRequestUseCase.registerLoanRequest(loanRequest))
                .expectErrorSatisfies(error -> {
                    assertThat(error)
                            .isInstanceOf(RuntimeException.class);
                    assertThat(error.getMessage())
                            .contains("Loan type not allowed");
                })
                .verify();
    }

    //Validar cuando el usuario no exite

}
