package co.com.pragma.model.user.exception;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ValidateInputDateException {

    private String name;
    private String lastName;
    private LocalDate birthdate;
    private String address;
    private String phone;
    private String email;
    private BigDecimal salary;

    public ValidateInputDateException(String message) {
        super();
    }

}
