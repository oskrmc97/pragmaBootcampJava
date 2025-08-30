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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoanRequestUseCaseTest {

    @Mock
    private LoanRequestRepository loanRequestRepository;

    @InjectMocks
    private LoanRequestUseCase loanRequestUseCase;
}
