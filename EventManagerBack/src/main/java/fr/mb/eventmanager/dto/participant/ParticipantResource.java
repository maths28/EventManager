package fr.mb.eventmanager.dto.participant;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ParticipantResource {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
}
