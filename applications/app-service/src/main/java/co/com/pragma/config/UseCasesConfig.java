package co.com.pragma.config;

import co.com.pragma.model.user.gateways.UserRepository;
import co.com.pragma.usecase.user.UserUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

        final static Logger logger = LoggerFactory.getLogger(UseCasesConfig.class);

        @Bean
        public UserUseCase userUseCase(UserRepository userRepository) {
                return new UserUseCase(userRepository, logger);
        }
}
