package fr.mb.eventmanager.service.implementation;

import fr.mb.eventmanager.controller.dto.EventResource;
import fr.mb.eventmanager.controller.dto.ParticipantCreateRequest;
import fr.mb.eventmanager.controller.dto.ParticipantResource;
import fr.mb.eventmanager.controller.dto.RegisterParticipantToEventRequest;
import fr.mb.eventmanager.exception.EventFullException;
import fr.mb.eventmanager.exception.EventNotFoundException;
import fr.mb.eventmanager.exception.ParticipantNotFoundException;
import fr.mb.eventmanager.exception.UserNotRegisteredForEventException;
import fr.mb.eventmanager.repository.EventRepository;
import fr.mb.eventmanager.repository.ParticipantRepository;
import fr.mb.eventmanager.repository.model.Event;
import fr.mb.eventmanager.repository.model.Participant;
import fr.mb.eventmanager.service.IParticipantService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipantServiceImpl implements IParticipantService {

    private final ParticipantRepository participantRepository;

    private final EventRepository eventRepository;

    public ParticipantServiceImpl(ParticipantRepository participantRepository, EventRepository eventRepository) {
        this.participantRepository = participantRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public ParticipantResource createParticipant(ParticipantCreateRequest participantCreateRequest) {
        return participantRepository.save(participantCreateRequest.toParticipant()).toParticipantResource();
    }

    @Override
    public List<EventResource> registerParticipantToEvent(int participantId, RegisterParticipantToEventRequest registerParticipantToEventRequest) throws ParticipantNotFoundException, EventNotFoundException, EventFullException {
        Participant participant = participantRepository.findById(participantId).orElseThrow(()-> new ParticipantNotFoundException(participantId));
        Event event = eventRepository.findById(registerParticipantToEventRequest.eventId())
                .orElseThrow(()-> new EventNotFoundException(registerParticipantToEventRequest.eventId()));
        if(event.getParticipants().size() == event.getMaxCapacity()) throw new EventFullException(registerParticipantToEventRequest.eventId());
        participant.addEvent(event);
        return participantRepository.save(participant).getEvents().stream().map(Event::toEventResource).toList();
    }

    @Override
    public List<EventResource> unregisterParticipantToEvent(int participantId, int eventId) throws ParticipantNotFoundException, UserNotRegisteredForEventException {
        Participant participant = participantRepository.findById(participantId).orElseThrow(()-> new ParticipantNotFoundException(participantId));
        if(!participant.removeEvent(eventId)) throw new UserNotRegisteredForEventException(eventId);
        return participantRepository.save(participant).getEvents().stream().map(Event::toEventResource).toList();
    }

    @Override
    public List<EventResource> findAllEventsForParticipant(int participantId) throws ParticipantNotFoundException {
        return participantRepository.findById(participantId).map(participant -> participant.getEvents().stream().map(Event::toEventResource).toList())
                .orElseThrow(()->new ParticipantNotFoundException(participantId));
    }
}
