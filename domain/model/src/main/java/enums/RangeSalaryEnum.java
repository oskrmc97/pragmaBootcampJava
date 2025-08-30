package enums;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public enum RangeSalaryEnum {

    MAX_SALARY(new BigDecimal("15000000")),
    MIN_SALARY(new BigDecimal("2"));

    private final BigDecimal value;

    private RangeSalaryEnum(BigDecimal value) {
        this.value = value;
    }

}
