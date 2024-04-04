package fr.mb.eventmanager.validator;

import fr.mb.eventmanager.annotation.StartDateAfterNow;
import fr.mb.eventmanager.model.Event;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class StartDateAfterNowValidator implements ConstraintValidator<StartDateAfterNow, LocalDateTime> {
    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
        //Si date de début à null, rendre valide pour n'afficher que l'erreur
        //de la contrainte NotNull
        return value == null || value.isAfter(LocalDateTime.now());
    }
}
