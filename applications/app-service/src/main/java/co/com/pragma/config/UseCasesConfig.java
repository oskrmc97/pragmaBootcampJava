package co.com.pragma.config;
import co.com.pragma.model.user.gateways.UserRepository;
import co.com.pragma.usecase.user.LogInUseCase;
import co.com.pragma.usecase.user.SignUpUseCase;
import co.com.pragma.usecase.user.UserUseCase;
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

    @Bean
    public SignUpUseCase signUpUseCase(UserRepository userRepository) {
        return new SignUpUseCase(userRepository);
    }

    @Bean
    public LogInUseCase logInUseCase(UserRepository userRepository) {
        return new LogInUseCase(userRepository);
    }

    @Bean
        public UserUseCase userUseCase(UserRepository userRepository) {
                return new UserUseCase(userRepository);
        }

}
