package fr.mb.eventmanager.dto.participant;

import fr.mb.eventmanager.dto.user.UserCreateRequest;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ParticipantCreateRequest extends UserCreateRequest {
    @NotEmpty(message = "Le nom est obligatoire")
    private String firstName;
    @NotEmpty(message = "Le prénom est obligatoire")
    private String lastName;
}
