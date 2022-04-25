package by.runets.hedgehog.validator.annotation;

import by.runets.hedgehog.validator.NotEmptyCodeConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotEmptyCodeConstraintValidator.class)
public @interface NotEmptyCode {
    String message() default "The request does not contain code value";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
