package co.com.pragma.model.loanRequest.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
@Getter
@Setter
public class UserDto {
    String name;
    String lastName;
    LocalDate birthdate;
    String address;
    String phone;
    String email;
    BigDecimal salary;
}
