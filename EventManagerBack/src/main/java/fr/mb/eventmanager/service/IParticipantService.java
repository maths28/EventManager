package fr.mb.eventmanager.service;

import fr.mb.eventmanager.dto.event.EventResource;
import fr.mb.eventmanager.exception.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IParticipantService {

    public List<EventResource> registerParticipantToEvent(int participantId, int eventId)
            throws ParticipantNotFoundException, EventNotFoundException, EventFullException;

    public List<EventResource> unregisterParticipantToEvent(int participantId,
                                                            int eventId
    ) throws ParticipantNotFoundException, UserNotRegisteredForEventException, EventAlreadyStartedOrDoneException;

    public Page<EventResource> findAllEventsForParticipant(int participantId, int pageSize, int pageNumber) throws ParticipantNotFoundException;

}
