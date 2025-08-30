package co.com.pragma.model.loanRequest.dto;

import java.math.BigDecimal;

public record LoanRequestOutDto(
        String document,
        String email,
        BigDecimal amount,
        Integer term,
        String loan_type,
        String status) {

}
