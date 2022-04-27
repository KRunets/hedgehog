package by.runets.hedgehog.validator;

import by.runets.hedgehog.exception.BadRequestException;
import by.runets.hedgehog.exception.ResourceValidationException;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintValidatorContext;
import java.util.function.Predicate;

import static by.runets.hedgehog.utils.ErrorMessageUtils.BAD_REQUEST_ERROR_MESSAGE;
import static by.runets.hedgehog.utils.ErrorMessageUtils.VALIDATION_ERROR_MESSAGE;

@Service
public class GenericConstraintValidator<T> {

    public boolean isValid(T t, ConstraintValidatorContext constraintValidatorContext, Predicate<T> predicate) {
        if (t == null) {
            throw new BadRequestException(BAD_REQUEST_ERROR_MESSAGE);
        }

        if (predicate.test(t)) {
            throw new ResourceValidationException(VALIDATION_ERROR_MESSAGE);
        }

        return true;
    }
}
