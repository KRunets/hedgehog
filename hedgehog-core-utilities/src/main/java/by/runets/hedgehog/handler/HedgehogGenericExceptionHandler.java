package by.runets.hedgehog.handler;

import by.runets.hedgehog.ErrorMessage;
import by.runets.hedgehog.exception.*;
import by.runets.hedgehog.utils.ErrorMessageUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class HedgehogGenericExceptionHandler {

    private static final Logger LOG = LogManager.getLogger(HedgehogGenericExceptionHandler.class);

    private static final Map<Class<? extends Exception>, HttpStatus> STATUS_MAP;

    static {
        STATUS_MAP = new HashMap<>();
        STATUS_MAP.put(ResourceNotFoundException.class, HttpStatus.NOT_FOUND);
        STATUS_MAP.put(ResourceValidationException.class, HttpStatus.UNPROCESSABLE_ENTITY);
        STATUS_MAP.put(MethodArgumentNotValidException.class, HttpStatus.BAD_REQUEST);
        STATUS_MAP.put(BadRequestException.class, HttpStatus.BAD_REQUEST);
        STATUS_MAP.put(HttpMessageNotReadableException.class, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ResourceNotFoundException.class, ResourceValidationException.class, ValidationException.class, MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorMessage> handleHedgehogException(Exception ex, WebRequest request) {
        final HttpStatus status = produceHttpStatus(ex);

        final ErrorMessage errorMessage = ErrorMessageUtils.buildErrorMessage(status.value(), ex.getMessage(), ExceptionUtils.getStackTrace(ex));

        LOG.error(errorMessage);

        return ResponseEntity
                .status(status)
                .body(errorMessage);
    }

    private HttpStatus produceHttpStatus(Exception hedgehogRuntimeException) {
        final HttpStatus status = STATUS_MAP.get(hedgehogRuntimeException.getClass());
        if (status != null) {
            return status;
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

}
