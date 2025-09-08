package co.com.pragma.model.loanRequest.exception;


import lombok.Getter;

@Getter
public class FieldValidationException extends RuntimeException {

    private final String field;

    public FieldValidationException(String field, String message) {
        super(message);
        this.field = field;
    }

    public FieldValidationException(String message) {
        super(message);
        field = null;
    }

}
