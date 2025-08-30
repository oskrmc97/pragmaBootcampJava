package co.com.pragma.api.exceptionHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(HttpStatus.CONFLICT)
public class ValidationExceptionHandler extends RuntimeException {

    public ValidationExceptionHandler(String message) {

        super(message);
        log.info("Validation error");

    }

    public ValidationExceptionHandler(String message, Throwable causa) {
        super(message, causa);

    }
}

