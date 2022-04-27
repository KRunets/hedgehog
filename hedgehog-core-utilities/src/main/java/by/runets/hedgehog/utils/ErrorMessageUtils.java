package by.runets.hedgehog.utils;

import by.runets.hedgehog.ErrorMessage;

public class ErrorMessageUtils {

    public static final String VALIDATION_ERROR_MESSAGE = "Validation failed: invalid / missing variables supplied.";
    public static final String BAD_REQUEST_ERROR_MESSAGE = "Malformed JSON passed.";

    private ErrorMessageUtils() {
    }

    public static ErrorMessage buildErrorMessage(int status, String cause, String stackTrace) {
        return new ErrorMessage(status, cause, stackTrace);
    }

}
