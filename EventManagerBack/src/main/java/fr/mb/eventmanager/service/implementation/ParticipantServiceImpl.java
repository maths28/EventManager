package fr.mb.eventmanager.service.implementation;

import fr.mb.eventmanager.dto.event.EventResource;
import fr.mb.eventmanager.exception.*;
import fr.mb.eventmanager.mapper.EventMapper;
import fr.mb.eventmanager.model.Event;
import fr.mb.eventmanager.model.Participant;
import fr.mb.eventmanager.repository.EventRepository;
import fr.mb.eventmanager.repository.ParticipantRepository;
import fr.mb.eventmanager.service.IParticipantService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParticipantServiceImpl implements IParticipantService {

    private final ParticipantRepository participantRepository;

    private final EventRepository eventRepository;

    private final EventMapper eventMapper;


    public ParticipantServiceImpl(ParticipantRepository participantRepository, EventRepository eventRepository, EventMapper eventMapper) {
        this.participantRepository = participantRepository;
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }

    @Override
    public List<EventResource> registerParticipantToEvent(int participantId, int eventId) throws ParticipantNotFoundException, EventNotFoundException, EventFullException {
        Participant participant = participantRepository.findById(participantId).orElseThrow(()-> new ParticipantNotFoundException(participantId));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(()-> new EventNotFoundException(eventId));
        if(event.getTotalParticipants() == event.getMaxCapacity()) throw new EventFullException(eventId);
        participant.addEvent(event);
        event.setTotalParticipants(event.getTotalParticipants()+1);
        return participantRepository.save(participant).getEvents().stream()
                .map(eventMapper::toEventResource).toList();
    }

    @Override
    public List<EventResource> unregisterParticipantToEvent(int participantId, int eventId)
            throws ParticipantNotFoundException,
            UserNotRegisteredForEventException,
            EventAlreadyStartedOrDoneException{
        Participant participant = participantRepository.findById(participantId).orElseThrow(()-> new ParticipantNotFoundException(participantId));
        if(!participant.removeEvent(eventId)){
            Optional<Event> event = participant.getEvents().stream()
                    .filter((eventOfPart -> eventOfPart.getId() == eventId)).findAny();
            if(event.isEmpty()){
                throw new UserNotRegisteredForEventException(participantId);
            } else {
                throw new EventAlreadyStartedOrDoneException(eventId);
            }
        }
        return participantRepository.save(participant).getEvents().stream()
                .map(eventMapper::toEventResource).toList();
    }

    @Override
    public Page<EventResource> findAllEventsForParticipant(int participantId, int pageSize, int pageNumber) throws ParticipantNotFoundException {
        if(!participantRepository.existsById(participantId)) throw new ParticipantNotFoundException(participantId);
        return eventRepository.findAllByParticipantsId(participantId, PageRequest.of(pageNumber, pageSize)).map(
                eventMapper::toEventResource
        );
    }
}
