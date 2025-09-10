package enums;

import lombok.Getter;

import java.math.BigInteger;

@Getter
public enum StatusLoanRequestEnum {

    PENDIENTE_REVISION("1");


    private final String value;

    StatusLoanRequestEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
