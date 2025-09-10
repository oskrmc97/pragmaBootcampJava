package enums;

import lombok.Getter;

import java.math.BigInteger;

@Getter
public enum LoanType {

    PERSONAL("1"),
    LIBRE("2"),
    VIVIENDA("3");

    private final String value;

    LoanType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static String fromString(String type) {
        return LoanType.valueOf(type).getValue();
    }
}
