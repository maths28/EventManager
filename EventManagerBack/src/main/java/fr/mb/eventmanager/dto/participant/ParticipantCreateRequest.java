package fr.mb.eventmanager.dto.participant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ParticipantCreateRequest {
    private String firstName;
    private String lastName;
    private String email;
}
