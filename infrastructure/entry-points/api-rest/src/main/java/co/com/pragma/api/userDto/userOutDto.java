package co.com.pragma.api.userDto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record userOutDto(String name, String lastName, LocalDate birthdate, String address, String phone, String email, BigDecimal salary) {
}
