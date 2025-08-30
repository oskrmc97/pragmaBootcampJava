package enums;

import lombok.Getter;

@Getter
public enum StatusLoanRequestEnum {

    PENDIETE_REVISION("Pendiente de revisión");

    private final String value;

    private StatusLoanRequestEnum(String value) {
        this.value = value;
    }
}
