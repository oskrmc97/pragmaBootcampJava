package co.com.pragma.api;

import co.com.pragma.model.loanRequest.dto.UserDto;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class UserClient {

    private final WebClient webClient;

    public UserClient(WebClient userServiceWebClient) {
        this.webClient = userServiceWebClient;
    }

    public Mono<UserDto> UserValidator(String email, String token) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/usuarios/email/{email}")
                        .build(email))
                .headers(headers -> headers.set(HttpHeaders.AUTHORIZATION, token))
                .retrieve()
                .bodyToMono(UserDto.class);
    }

    public Mono<UserDto> UserAdviserValidator(String token) {
        return webClient.get()
                .uri("/api/v1/adviser")
                .headers(headers -> headers.set(HttpHeaders.AUTHORIZATION, token))
                .retrieve()
                .bodyToMono(UserDto.class);
    }

}