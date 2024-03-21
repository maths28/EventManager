package fr.mb.eventmanager.service.implementation;

import fr.mb.eventmanager.dto.event.EventResource;
import fr.mb.eventmanager.dto.participant.ParticipantCreateRequest;
import fr.mb.eventmanager.dto.participant.ParticipantResource;
import fr.mb.eventmanager.exception.EventFullException;
import fr.mb.eventmanager.exception.EventNotFoundException;
import fr.mb.eventmanager.exception.ParticipantNotFoundException;
import fr.mb.eventmanager.exception.UserNotRegisteredForEventException;
import fr.mb.eventmanager.repository.EventRepository;
import fr.mb.eventmanager.repository.ParticipantRepository;
import fr.mb.eventmanager.model.Event;
import fr.mb.eventmanager.model.Participant;
import fr.mb.eventmanager.service.IParticipantService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipantServiceImpl implements IParticipantService {

    private final ParticipantRepository participantRepository;

    private final EventRepository eventRepository;

    private final ModelMapper modelMapper;


    public ParticipantServiceImpl(ParticipantRepository participantRepository, EventRepository eventRepository, ModelMapper modelMapper) {
        this.participantRepository = participantRepository;
        this.eventRepository = eventRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ParticipantResource createParticipant(ParticipantCreateRequest participantCreateRequest) {
        Participant participant = participantRepository.save(
                modelMapper.map(participantCreateRequest, Participant.class)
        );
        return modelMapper.map(participant, ParticipantResource.class);
    }

    @Override
    public List<EventResource> registerParticipantToEvent(int participantId, int eventId) throws ParticipantNotFoundException, EventNotFoundException, EventFullException {
        Participant participant = participantRepository.findById(participantId).orElseThrow(()-> new ParticipantNotFoundException(participantId));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(()-> new EventNotFoundException(eventId));
        if(event.getParticipants().size() == event.getMaxCapacity()) throw new EventFullException(eventId);
        participant.addEvent(event);
        return participantRepository.save(participant).getEvents().stream()
                .map(eventOfParticipant -> modelMapper.map(eventOfParticipant, EventResource.class)).toList();
    }

    @Override
    public List<EventResource> unregisterParticipantToEvent(int participantId, int eventId) throws ParticipantNotFoundException, UserNotRegisteredForEventException {
        Participant participant = participantRepository.findById(participantId).orElseThrow(()-> new ParticipantNotFoundException(participantId));
        if(!participant.removeEvent(eventId)) throw new UserNotRegisteredForEventException(eventId);
        return participantRepository.save(participant).getEvents().stream()
                .map(eventOfParticipant -> modelMapper.map(eventOfParticipant, EventResource.class)).toList();
    }

    @Override
    public List<EventResource> findAllEventsForParticipant(int participantId) throws ParticipantNotFoundException {
        return participantRepository.findById(participantId).map(
                participant -> participant.getEvents().stream().map(
                        eventOfParticipant -> modelMapper.map(eventOfParticipant, EventResource.class)
                ).toList()
        ).orElseThrow(()->new ParticipantNotFoundException(participantId));
    }
}
