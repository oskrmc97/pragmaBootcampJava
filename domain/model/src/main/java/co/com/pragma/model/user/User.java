package co.com.pragma.model.user;
import lombok.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {
    private String name;
    private String lastName;
    private LocalDate birthdate;
    private String address;
    private String phone;
    private String email;
    private String document;
    private BigDecimal salary;
    private String rolUser;
    private String password;
}
