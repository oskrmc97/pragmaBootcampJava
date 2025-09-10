package co.com.pragma.r2dbc.security.jwt.service;

import co.com.pragma.model.user.gateways.JwtService;
import co.com.pragma.r2dbc.security.jwt.provider.JwtProvider;
import org.springframework.stereotype.Component;

@Component
public class JwtServiceImpl implements JwtService {

    private final JwtProvider jwtProvider;

    public JwtServiceImpl(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public String getEmailFromToken(String token) {
        return jwtProvider.getClaims(token.replace("Bearer ", "")).getSubject();
    }
}