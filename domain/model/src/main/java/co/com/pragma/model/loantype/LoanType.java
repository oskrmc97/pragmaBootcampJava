package co.com.pragma.model.loantype;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
//import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class LoanType {

    private BigInteger idLoanType;
    private String name;
    private BigDecimal minAmount;
    private BigDecimal maxAmount;
    private Double interestRate;
    private Boolean autoValidation;
}
