package by.runets.hedgehog.validator;

import by.runets.hedgehog.validator.annotation.ValidateSlug;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

import static by.runets.hedgehog.utils.Constants.EMAIL_VERIFICATION;
import static by.runets.hedgehog.utils.Constants.MOBILE_VERIFICATION;

public class SlugAllowedValuesConstraintValidator implements ConstraintValidator<ValidateSlug, String> {

    private static final List<String> SLUG_ALLOWED_VALUES = Arrays.asList(EMAIL_VERIFICATION, MOBILE_VERIFICATION);

    @Autowired
    private GenericConstraintValidator<String> genericConstraintValidator;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return genericConstraintValidator.isValid(value, val -> !SLUG_ALLOWED_VALUES.contains(val));
    }
}
