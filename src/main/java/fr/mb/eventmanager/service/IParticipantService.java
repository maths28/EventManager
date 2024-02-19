package fr.mb.eventmanager.service;

import fr.mb.eventmanager.controller.dto.EventResource;
import fr.mb.eventmanager.controller.dto.ParticipantCreateRequest;
import fr.mb.eventmanager.controller.dto.ParticipantResource;
import fr.mb.eventmanager.controller.dto.RegisterParticipantToEventRequest;
import fr.mb.eventmanager.exception.EventFullException;
import fr.mb.eventmanager.exception.EventNotFoundException;
import fr.mb.eventmanager.exception.ParticipantNotFoundException;
import fr.mb.eventmanager.exception.UserNotRegisteredForEventException;
import fr.mb.eventmanager.repository.model.Event;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IParticipantService {

    public ParticipantResource createParticipant(ParticipantCreateRequest participantCreateRequest);

    public List<EventResource> registerParticipantToEvent(int participantId,
                                                          RegisterParticipantToEventRequest registerParticipantToEventRequest)
            throws ParticipantNotFoundException, EventNotFoundException, EventFullException;

    public List<EventResource> unregisterParticipantToEvent(int participantId,
                                                            int eventId
    ) throws ParticipantNotFoundException, UserNotRegisteredForEventException;

    public List<EventResource> findAllEventsForParticipant(int participantId) throws ParticipantNotFoundException;

}
