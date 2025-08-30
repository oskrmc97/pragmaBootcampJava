package co.com.pragma.model.loanRequest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanRequestIntDto {
    String document;
    String email;
    BigDecimal amount;
    Integer term;
    String loan_type;
    String status;
}
