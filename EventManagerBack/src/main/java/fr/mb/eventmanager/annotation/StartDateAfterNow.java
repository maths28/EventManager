package fr.mb.eventmanager.annotation;

import fr.mb.eventmanager.validator.StartDateAfterNowValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = StartDateAfterNowValidator.class)
public @interface StartDateAfterNow
{
    String message() default "La date de début doit être dans le futur";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
