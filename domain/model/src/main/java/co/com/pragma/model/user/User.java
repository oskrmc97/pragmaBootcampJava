package co.com.pragma.model.user;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
//import lombok.NoArgsConstructor;


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
    private BigDecimal salary;
}
