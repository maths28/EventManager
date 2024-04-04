package fr.mb.eventmanager.validator;

import fr.mb.eventmanager.annotation.EndDateAfterStartDate;
import fr.mb.eventmanager.annotation.StartDateAfterNow;
import fr.mb.eventmanager.dto.event.EventCreateOrUpdateRequest;
import fr.mb.eventmanager.model.Event;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class EndDateAfterStartDateValidator implements ConstraintValidator<EndDateAfterStartDate, EventCreateOrUpdateRequest> {
    @Override
    public boolean isValid(EventCreateOrUpdateRequest value, ConstraintValidatorContext context) {
        //Si la date de debut ou fin est nulle, rendre valide pour n'afficher que l'erreur
        //de la contrainte NotNull
        return value.getStartDate() == null ||
                value.getEndDate() == null ||
                value.getEndDate().isAfter(value.getStartDate());
    }
}
