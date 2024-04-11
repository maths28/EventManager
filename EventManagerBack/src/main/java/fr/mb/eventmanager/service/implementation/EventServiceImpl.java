package fr.mb.eventmanager.service.implementation;

import fr.mb.eventmanager.dto.event.EventCreateOrUpdateRequest;
import fr.mb.eventmanager.dto.event.EventResource;
import fr.mb.eventmanager.dto.participant.ParticipantResource;
import fr.mb.eventmanager.exception.EventNotFoundException;
import fr.mb.eventmanager.exception.MaxCapacitySmallerThanTotalPartException;
import fr.mb.eventmanager.mapper.EventMapper;
import fr.mb.eventmanager.mapper.ParticipantMapper;
import fr.mb.eventmanager.model.Event;
import fr.mb.eventmanager.repository.EventRepository;
import fr.mb.eventmanager.repository.ParticipantRepository;
import fr.mb.eventmanager.service.IEventService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements IEventService {

    private final EventRepository eventRepository;
    private final ParticipantRepository participantRepository;
    private final EventMapper eventMapper;
    private final ParticipantMapper participantMapper;

    public EventServiceImpl(EventRepository eventRepository, ParticipantRepository participantRepository, EventMapper eventMapper, ParticipantMapper participantMapper) {
        this.eventRepository = eventRepository;
        this.participantRepository = participantRepository;
        this.eventMapper = eventMapper;
        this.participantMapper = participantMapper;
    }

    @Override
    public EventResource getEvent(int eventId) throws EventNotFoundException{
        return this.eventRepository.findById(eventId).map(eventMapper::toEventResource)
                .orElseThrow(()->new EventNotFoundException(eventId));
    }

    @Override
    public EventResource saveEvent(EventCreateOrUpdateRequest eventSaveRequest) {
        Event event = this.eventRepository.save(eventMapper.toEvent(eventSaveRequest));
        return eventMapper.toEventResource(event);
    }

    @Override
    public EventResource updateEvent(int eventId, EventCreateOrUpdateRequest eventUpdateRequest) throws EventNotFoundException, MaxCapacitySmallerThanTotalPartException {
        if(!this.eventRepository.existsById(eventId)) throw new EventNotFoundException(eventId);
        Integer totalParticipantsOfEvent = this.participantRepository.countByEventsId(eventId);
        if(totalParticipantsOfEvent > eventUpdateRequest.getMaxCapacity())
            throw new MaxCapacitySmallerThanTotalPartException(totalParticipantsOfEvent);
        Event event = eventMapper.toEvent(eventUpdateRequest);
        event.setId(eventId);
        return eventMapper.toEventResource(eventRepository.save(event));
    }

    @Override
    public void deleteEvent(int eventId) throws EventNotFoundException {
        Event event = eventRepository.findById(eventId).orElseThrow(()-> new EventNotFoundException(eventId));
        event.getParticipants().forEach(participant -> {
            participant.removeEvent(eventId);
            participantRepository.save(participant);
        });
        eventRepository.delete(event);
    }

    @Override
    public Page<EventResource> findAllFutureEvents(
            String location,
            Integer excludeParticipantId,
            int pageSize,
            int pageNumber
    ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return eventRepository.findAllFutureEvents(location, excludeParticipantId, pageable)
                .map(eventMapper::toEventResource);
    }

    @Override
    public Page<ParticipantResource> findAllParticipantsForEvent(int eventId, int pageSize, int pageNumber) throws EventNotFoundException {
        if(!this.eventRepository.existsById(eventId)) throw new EventNotFoundException(eventId);
        return this.participantRepository.findAllByEventsId(eventId, PageRequest.of(pageNumber,pageSize))
                .map(participantMapper::toParticipantResource);
    }
}
