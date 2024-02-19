package fr.mb.eventmanager.service.implementation;

import fr.mb.eventmanager.controller.dto.EventResource;
import fr.mb.eventmanager.controller.dto.EventCreateOrUpdateRequest;
import fr.mb.eventmanager.controller.dto.ParticipantResource;
import fr.mb.eventmanager.exception.EventNotFoundException;
import fr.mb.eventmanager.repository.EventRepository;
import fr.mb.eventmanager.repository.ParticipantRepository;
import fr.mb.eventmanager.repository.model.Event;
import fr.mb.eventmanager.repository.model.Participant;
import fr.mb.eventmanager.service.IEventService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements IEventService {

    private final EventRepository eventRepository;
    private final ParticipantRepository participantRepository;

    public EventServiceImpl(EventRepository eventRepository, ParticipantRepository participantRepository) {
        this.eventRepository = eventRepository;
        this.participantRepository = participantRepository;
    }

    @Override
    public EventResource saveEvent(EventCreateOrUpdateRequest eventSaveRequest) {
        return this.eventRepository.save(eventSaveRequest.toEvent()).toEventResource();
    }

    @Override
    public EventResource updateEvent(int eventId, EventCreateOrUpdateRequest eventUpdateRequest) throws EventNotFoundException {
        if(!this.eventRepository.existsById(eventId)) throw new EventNotFoundException(eventId);
        Event event = eventUpdateRequest.toEvent();
        event.setId(eventId);
        return eventRepository.save(event).toEventResource();
    }

    @Override
    public void deleteEvent(int eventId) throws EventNotFoundException {
        eventRepository.findById(eventId).map(event -> {
            event.getParticipants().forEach(participant -> {
                participant.removeEvent(eventId);
                participantRepository.save(participant);
            });
            eventRepository.delete(event);
            return true;
        }).orElseThrow(()-> new EventNotFoundException(eventId));
    }

    @Override
    public List<EventResource> findAllFutureEvents(String location, int pageSize, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize);
        return eventRepository.findAllFutureEvents(location, pageable).stream().map(Event::toEventResource).toList();
    }

    @Override
    public List<ParticipantResource> findAllParticipantsForEvent(int eventId) throws EventNotFoundException {
        return eventRepository.findById(eventId)
                .map(event -> event.getParticipants().stream().map(Participant::toParticipantResource).toList())
                .orElseThrow(()-> new EventNotFoundException(eventId));
    }
}
