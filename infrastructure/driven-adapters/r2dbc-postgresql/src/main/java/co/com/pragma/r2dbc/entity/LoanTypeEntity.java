package co.com.pragma.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.annotation.Id;
import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table("loan_type")
public class LoanTypeEntity {

    @Column("id_loan_type")
    @Id
    private BigInteger idLoanType;
    private String name;
    private BigDecimal minAmount;
    private BigDecimal maxAmount;
    private Double interestRate;
    private Boolean autoValidation;
}
