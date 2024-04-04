package fr.mb.eventmanager.dto.participant;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ParticipantCreateRequest {
    @NotEmpty(message = "Le nom est obligatoire")
    private String firstName;
    @NotEmpty(message = "Le prénom est obligatoire")
    private String lastName;
    @NotEmpty(message = "Le mail est obligatoire")
    private String email;
}
