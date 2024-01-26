package faheem.microservices.belan.exceptions;

public class BelanNotFoundException extends RuntimeException {
    public BelanNotFoundException(String message) {
        super(message);
    }
}
