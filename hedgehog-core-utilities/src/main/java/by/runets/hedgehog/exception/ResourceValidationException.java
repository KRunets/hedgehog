package by.runets.hedgehog.exception;

import javax.validation.ConstraintDeclarationException;

public class ResourceValidationException extends ConstraintDeclarationException {
    public ResourceValidationException() {
    }

    public ResourceValidationException(String message) {
        super(message);
    }

    public ResourceValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceValidationException(Throwable cause) {
        super(cause);
    }

}
