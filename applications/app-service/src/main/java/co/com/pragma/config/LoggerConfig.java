package co.com.pragma.config;

import co.com.pragma.api.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggerConfig {
    @Bean
    public Logger handlerLogger() {
        return LoggerFactory.getLogger(Handler.class);
    }
}
