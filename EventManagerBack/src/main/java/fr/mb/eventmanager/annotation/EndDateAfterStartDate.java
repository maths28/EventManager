package fr.mb.eventmanager.annotation;

import fr.mb.eventmanager.validator.EndDateAfterStartDateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = EndDateAfterStartDateValidator.class)
public @interface EndDateAfterStartDate
{
    String message() default "La date de fin doit être supérieure à la date de début";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
