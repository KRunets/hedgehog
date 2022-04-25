package by.runets.hedgehog.validator;

import by.runets.hedgehog.exception.BadRequestException;
import by.runets.hedgehog.exception.ResourceValidationException;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintValidatorContext;
import javax.validation.metadata.ConstraintDescriptor;
import java.util.function.Predicate;

import static by.runets.hedgehog.utils.ErrorMessageUtils.BAD_REQUEST_ERROR_MESSAGE;

@Service
public class GenericConstraintValidator<T> {

    public boolean isValid(T t, ConstraintValidatorContext constraintValidatorContext, Predicate<T> predicate) {
        if (t == null) {
            throw new BadRequestException(BAD_REQUEST_ERROR_MESSAGE);
        }

        if (predicate.test(t)) {
            final String messageTemplate = extractMessageTemplate(constraintValidatorContext);
            throw new ResourceValidationException(messageTemplate);
        }

        return true;
    }

    private String extractMessageTemplate(ConstraintValidatorContext constraintValidatorContext) {
        if (constraintValidatorContext instanceof ConstraintValidatorContextImpl) {
            final ConstraintValidatorContextImpl constraintValidatorContextImpl = (ConstraintValidatorContextImpl)constraintValidatorContext;
            final ConstraintDescriptor<?> constraintDescriptor = constraintValidatorContextImpl.getConstraintDescriptor();
            if (constraintDescriptor != null) {
                return constraintDescriptor.getMessageTemplate();
            }
        }
        return StringUtils.EMPTY;
    }
}
