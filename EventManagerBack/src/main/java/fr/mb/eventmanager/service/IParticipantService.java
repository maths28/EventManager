package fr.mb.eventmanager.service;

import fr.mb.eventmanager.dto.event.EventResource;
import fr.mb.eventmanager.dto.participant.ParticipantCreateRequest;
import fr.mb.eventmanager.dto.participant.ParticipantResource;
import fr.mb.eventmanager.exception.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IParticipantService {

    public ParticipantResource createParticipant(ParticipantCreateRequest participantCreateRequest)
            throws ParticipantEmailAlreadyExistsException;

    public List<EventResource> registerParticipantToEvent(int participantId, int eventId)
            throws ParticipantNotFoundException, EventNotFoundException, EventFullException;

    public List<EventResource> unregisterParticipantToEvent(int participantId,
                                                            int eventId
    ) throws ParticipantNotFoundException, UserNotRegisteredForEventException;

    public Page<EventResource> findAllEventsForParticipant(int participantId, int pageSize, int pageNumber) throws ParticipantNotFoundException;

    public ParticipantResource findParticipantByEmail(String email);

}
