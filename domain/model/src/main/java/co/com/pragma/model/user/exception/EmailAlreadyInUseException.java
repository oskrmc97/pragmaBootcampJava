package co.com.pragma.model.user.exception;


import lombok.Getter;

@Getter
public class EmailAlreadyInUseException extends RuntimeException {

    private final String email;

    public EmailAlreadyInUseException(String email, String message) {
        super(message);
        this.email = email;
    }

    public EmailAlreadyInUseException(String message) {
        super(message);
        email = null;
    }

}
