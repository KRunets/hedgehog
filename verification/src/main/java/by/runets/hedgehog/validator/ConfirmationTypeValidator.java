package by.runets.hedgehog.validator;

import by.runets.hedgehog.validator.annotation.ValidateConfirmationType;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class ConfirmationTypeValidator implements ConstraintValidator<ValidateConfirmationType, String> {

    private static final List<String> CONFIRMATION_VALUES = Arrays.asList("email_confirmation", "mobile_confirmation");

    @Autowired
    private GenericConstraintValidator<String> genericConstraintValidator;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return genericConstraintValidator.isValid(value, constraintValidatorContext, val -> !CONFIRMATION_VALUES.contains(val));
    }
}
