package by.runets.hedgehog.validator;

import by.runets.hedgehog.validator.annotation.NotEmptyCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;

public class NotEmptyCodeConstraintValidator implements ConstraintValidator<NotEmptyCode, Map<String, String>> {

    private static final String CODE = "code";

    @Autowired
    private GenericConstraintValidator<Map<String, String>> genericConstraintValidator;

    @Override
    public boolean isValid(Map<String, String> map, ConstraintValidatorContext constraintValidatorContext) {
        return genericConstraintValidator.isValid(map, val -> StringUtils.isEmpty(val.get(CODE)));
    }

    public void setGenericConstraintValidator(GenericConstraintValidator<Map<String, String>> genericConstraintValidator) {
        this.genericConstraintValidator = genericConstraintValidator;
    }
}
