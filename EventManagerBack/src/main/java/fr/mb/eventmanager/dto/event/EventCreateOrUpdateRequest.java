package fr.mb.eventmanager.dto.event;

import fr.mb.eventmanager.annotation.EndDateAfterStartDate;
import fr.mb.eventmanager.annotation.StartDateAfterNow;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@EndDateAfterStartDate
public class EventCreateOrUpdateRequest {
    @NotEmpty(message = "Le nom est obligatoire")
    private String name;
    @NotEmpty(message = "La description est obligatoire")
    private String description;
    @StartDateAfterNow
    @NotNull(message = "La date de début est obligatoire")
    private LocalDateTime startDate;
    @NotNull(message = "La date de fin est obligatoire")
    private LocalDateTime endDate;
    @NotEmpty(message = "Le lieu est obligatoire")
    private String location;
    @NotNull(message = "La capacité maximale est obligatoire")
    @Min(value = 1, message = "Un minimum d'une personne est requise pour l'évènement")
    private Integer maxCapacity;
}
