package by.runets.hedgehog.validator;

import by.runets.hedgehog.validator.annotation.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class VerificationCodeValidator implements ConstraintValidator<Code, String> {

    @Autowired
    private GenericConstraintValidator<String> genericConstraintValidator;

    @Value("#{new Long('${code.verification.length:8}')}")
    private Long codeVerificationLength;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return genericConstraintValidator.isValid(value, val -> val == null || val.isBlank() || val.length() != codeVerificationLength);
    }
}
