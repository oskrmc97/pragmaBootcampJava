package co.com.pragma.api.config;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class SecurityHeadersFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        exchange.getResponse()
                .getHeaders()
                .add("X-Content-Type-Options", "nosniff");
        exchange.getResponse()
                .getHeaders()
                .add("X-Frame-Options", "DENY");
        exchange.getResponse()
                .getHeaders()
                .add("X-XSS-Protection", "1; mode=block");
        exchange.getResponse()
                .getHeaders()
                .add("Cache-Control", "no-cache, no-store, must-revalidate");

        return chain.filter(exchange);
    }
}

