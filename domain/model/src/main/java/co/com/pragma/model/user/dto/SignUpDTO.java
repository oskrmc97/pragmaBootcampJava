package co.com.pragma.model.user.dto;

public record SignUpDTO(String name,
                        String lastName,
                        String email,
                        String password) {}