package co.com.pragma.model.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserIntDto {
    String name;
    String lastName;
    LocalDate birthdate;
    String address;
    String phone;
    String email;
    BigDecimal salary;
}
