package co.com.pragma.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalErrorWebExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public Mono<ResponseEntity<String>> handleRuntimeException(RuntimeException ex) {
        return Mono.just(
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ex.getMessage())
        );
    }
}
