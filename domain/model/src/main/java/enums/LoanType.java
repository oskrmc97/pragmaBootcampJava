package enums;

import lombok.Getter;

import java.math.BigInteger;

@Getter
public enum LoanType {

    LIBRE_INVERSION(BigInteger.valueOf(1)),
    VEHICULO(BigInteger.valueOf(2)),
    VIVIENDA(BigInteger.valueOf(3)),
    ESTUDIO(BigInteger.valueOf(4));

    private final BigInteger value;

    LoanType(BigInteger value) {
        this.value = value;
    }

    public BigInteger getValue() {
        return value;
    }

    public static BigInteger fromString(String type) {
        return LoanType.valueOf(type).getValue();
    }
}
