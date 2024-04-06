package fr.mb.eventmanager.mapper;

import fr.mb.eventmanager.dto.participant.ParticipantCreateRequest;
import fr.mb.eventmanager.dto.participant.ParticipantResource;
import fr.mb.eventmanager.model.Participant;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ParticipantMapper {
    Participant toParticipant(ParticipantCreateRequest participantCreateRequest);

    ParticipantResource toParticipantResource(Participant participant);
}
