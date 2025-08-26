package co.com.pragma.model.user.exception;

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

    public String getEmail() {
        return email;
    }
}
