package co.com.pragma.model.user.gateways;

public interface JwtService {
    String getEmailFromToken(String token);
}
