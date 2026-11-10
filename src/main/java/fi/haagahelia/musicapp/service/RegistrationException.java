package fi.haagahelia.musicapp.service;

// Reports a registration problem and the form field it belongs to.
public class RegistrationException extends RuntimeException {

    private final String field;

    public RegistrationException(String field, String message) {
        //parent class constructor call 
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}