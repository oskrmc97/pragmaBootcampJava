package co.com.pragma.model.user.dto;

import co.com.pragma.model.user.RolUser;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

public record userOutDto(String name,
        String lastName,
        LocalDate birthdate,
        String address,
        String phone,
        String email,
        String document,
        BigDecimal salary,
        String rolUser) {
}
