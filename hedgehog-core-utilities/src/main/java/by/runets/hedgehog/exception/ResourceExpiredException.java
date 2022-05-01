package by.runets.hedgehog.exception;

public class ResourceExpiredException extends RuntimeException {
    public ResourceExpiredException() {
    }

    public ResourceExpiredException(String message) {
        super(message);
    }

    public ResourceExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceExpiredException(Throwable cause) {
        super(cause);
    }

    public ResourceExpiredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
