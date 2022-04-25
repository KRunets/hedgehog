package by.runets.hedgehog.validator.annotation;

import by.runets.hedgehog.validator.SlugAllowedValuesConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SlugAllowedValuesConstraintValidator.class)
public @interface SlugAllowedValues {
    String message() default "The provided value is out of scope of allowed slug values";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
