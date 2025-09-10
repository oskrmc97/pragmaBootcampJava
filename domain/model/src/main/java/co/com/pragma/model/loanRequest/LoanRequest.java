package co.com.pragma.model.loanRequest;
import lombok.*;

import java.math.BigDecimal;
import java.math.BigInteger;
//import lombok.NoArgsConstructor;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class LoanRequest {
    private String document;
    private String email;
    private BigDecimal amount;
    private Integer term;
    private BigInteger loan_type;
    private BigInteger status;

}
