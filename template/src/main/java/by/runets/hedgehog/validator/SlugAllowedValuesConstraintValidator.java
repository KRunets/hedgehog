package by.runets.hedgehog.validator;

import by.runets.hedgehog.validator.annotation.SlugAllowedValues;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class SlugAllowedValuesConstraintValidator implements ConstraintValidator<SlugAllowedValues, String> {

    private static final List<String> SLUG_ALLOWED_VALUES = Arrays.asList("email-verification", "mobile-verification");

    @Autowired
    private GenericConstraintValidator<String> genericConstraintValidator;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return genericConstraintValidator.isValid(value, constraintValidatorContext, val -> !SLUG_ALLOWED_VALUES.contains(val));
    }
}
