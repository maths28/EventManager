package fr.mb.eventmanager.service;

import fr.mb.eventmanager.dto.event.EventResource;
import fr.mb.eventmanager.dto.participant.ParticipantCreateRequest;
import fr.mb.eventmanager.dto.participant.ParticipantResource;
import fr.mb.eventmanager.exception.EventFullException;
import fr.mb.eventmanager.exception.EventNotFoundException;
import fr.mb.eventmanager.exception.ParticipantNotFoundException;
import fr.mb.eventmanager.exception.UserNotRegisteredForEventException;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface IParticipantService {

    public ParticipantResource createParticipant(ParticipantCreateRequest participantCreateRequest);

    public List<EventResource> registerParticipantToEvent(int participantId, int eventId)
            throws ParticipantNotFoundException, EventNotFoundException, EventFullException;

    public List<EventResource> unregisterParticipantToEvent(int participantId,
                                                            int eventId
    ) throws ParticipantNotFoundException, UserNotRegisteredForEventException;

    public List<EventResource> findAllEventsForParticipant(int participantId) throws ParticipantNotFoundException;

    public ParticipantResource findParticipantByEmail(String email);

}
