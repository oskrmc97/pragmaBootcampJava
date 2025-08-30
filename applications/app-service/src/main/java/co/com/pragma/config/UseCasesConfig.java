package co.com.pragma.config;
import co.com.pragma.model.loanRequest.gateways.LoanRequestRepository;
import co.com.pragma.usecase.user.LoanRequestUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = "co.com.pragma.usecase",
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+UseCase$")
        },
        useDefaultFilters = false)

public class UseCasesConfig {

}
