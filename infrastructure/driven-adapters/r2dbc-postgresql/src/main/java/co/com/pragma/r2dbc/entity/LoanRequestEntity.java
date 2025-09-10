package co.com.pragma.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("loan_request_entity")
public class LoanRequestEntity {

    @Column
    @Id
    private BigInteger id;
    private String document;
    private String email;
    private BigDecimal amount;
    private Integer term;
    @Column("id_loan_type")
    private BigInteger loan_type;
    @Column("id_state")
    private BigInteger status;
}
