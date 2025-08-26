package co.com.pragma.model.user.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record userOutDto(String name, String lastName, LocalDate birthdate, String address, String phone, String email, BigDecimal salary) {
}
