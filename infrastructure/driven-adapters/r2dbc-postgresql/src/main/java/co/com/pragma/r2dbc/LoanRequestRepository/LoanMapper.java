package co.com.pragma.r2dbc.LoanRequestRepository;

import co.com.pragma.model.LoanStatus.LoanStatus;
import co.com.pragma.model.loanRequest.LoanRequest;
import co.com.pragma.model.loantype.LoanType;
import co.com.pragma.r2dbc.entity.LoanRequestEntity;
import co.com.pragma.r2dbc.entity.LoanStatusEntity;
import co.com.pragma.r2dbc.entity.LoanTypeEntity;

public class LoanMapper {
    public static LoanRequest toDomain(LoanRequestEntity loanRequest, LoanTypeEntity loanType, LoanStatusEntity loanStatusEntity) {
        LoanType loanTypeMap = LoanType.builder()
                .idLoanType(loanType.getIdLoanType())
                .name(loanType.getName())
                .minAmount(loanType.getMinAmount())
                .maxAmount(loanType.getMaxAmount())
                .interestRate(loanType.getInterestRate())
                .autoValidation(loanType.getAutoValidation())
                .build();

        LoanStatus loanStatusMap = LoanStatus.builder()
                .idStatus(loanStatusEntity.getStatusId())
                .name(loanStatusEntity.getName())
                .description(loanStatusEntity.getDescription())
                .build();


        return LoanRequest.builder()
                .document(loanRequest.getDocument())
                .email(loanRequest.getEmail())
                .amount(loanRequest.getAmount())
                .term(loanRequest.getTerm())
                .loan_type(loanTypeMap.getName())
                .interestRate(loanTypeMap.getInterestRate())
                .email(loanRequest.getEmail())
                .status(loanStatusMap.getName())
                .build();
    }



}
