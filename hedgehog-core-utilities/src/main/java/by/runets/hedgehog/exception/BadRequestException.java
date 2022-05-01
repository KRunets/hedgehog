package by.runets.hedgehog.exception;

import javax.validation.ConstraintDeclarationException;

public class BadRequestException extends ConstraintDeclarationException {
    public BadRequestException() {
    }

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadRequestException(Throwable cause) {
        super(cause);
    }

}
