package co.com.pragma.r2dbc.security.jwt.filter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class JwtFilter implements WebFilter {

    private static final List<String> EXCLUDED_PATHS = List.of(
            "/auth",
            "/swagger-ui.html",
            "/swagger-ui/",
            "/swagger-ui/index.html",
            "/swagger-ui",
            "/swagger-ui/**",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/webjars/"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().value();

        if (EXCLUDED_PATHS.stream().anyMatch(path::startsWith)) {
            return chain.filter(exchange);
        }
        String auth = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if(auth == null)
            return Mono.error(new Throwable("no token was found"));
        if(!auth.startsWith("Bearer "))
            return Mono.error(new Throwable("invalid auth"));
        String token = auth.replace("Bearer ", "");
        exchange.getAttributes().put("token", token);
        return chain.filter(exchange);
    }
}
