package enums;

import lombok.Getter;

@Getter
public enum LoanType {

    VIVIENDA("Vivienda"),
    LIBRE_INVERSION("Libre Inversión"),
    ESTUDIO("Estudio"),
    VEHICULO("Vehículo");

    private final String value;

    private LoanType(String value) {
        this.value = value;
    }
}
