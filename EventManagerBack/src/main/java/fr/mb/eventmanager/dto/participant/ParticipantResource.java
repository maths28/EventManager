package fr.mb.eventmanager.dto.participant;

import fr.mb.eventmanager.dto.user.UserResource;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ParticipantResource extends UserResource {
    private String firstName;
    private String lastName;
}
