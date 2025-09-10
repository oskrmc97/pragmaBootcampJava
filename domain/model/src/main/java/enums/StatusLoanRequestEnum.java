package enums;

import lombok.Getter;

import java.math.BigInteger;

@Getter
public enum StatusLoanRequestEnum {

    PENDIENTE_REVISION(BigInteger.valueOf(1));


    private final BigInteger value;

    StatusLoanRequestEnum(BigInteger value) {
        this.value = value;
    }

    public BigInteger getValue() {
        return value;
    }

    public static BigInteger fromString(String type) {
        return LoanType.valueOf(type).getValue();
    }
}
