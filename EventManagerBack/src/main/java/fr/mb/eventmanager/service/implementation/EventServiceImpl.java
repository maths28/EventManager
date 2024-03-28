package fr.mb.eventmanager.service.implementation;

import fr.mb.eventmanager.dto.event.EventCreateOrUpdateRequest;
import fr.mb.eventmanager.dto.event.EventResource;
import fr.mb.eventmanager.dto.participant.ParticipantResource;
import fr.mb.eventmanager.exception.EventNotFoundException;
import fr.mb.eventmanager.model.Event;
import fr.mb.eventmanager.repository.EventRepository;
import fr.mb.eventmanager.repository.ParticipantRepository;
import fr.mb.eventmanager.service.IEventService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements IEventService {

    private final EventRepository eventRepository;
    private final ParticipantRepository participantRepository;
    private final ModelMapper modelMapper;

    public EventServiceImpl(EventRepository eventRepository, ParticipantRepository participantRepository, ModelMapper modelMapper) {
        this.eventRepository = eventRepository;
        this.participantRepository = participantRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public EventResource getEvent(int eventId) throws EventNotFoundException{
        return this.eventRepository.findById(eventId).map((event -> modelMapper.map(event, EventResource.class)))
                .orElseThrow(()->new EventNotFoundException(eventId));
    }

    @Override
    public EventResource saveEvent(EventCreateOrUpdateRequest eventSaveRequest) {
        Event event = this.eventRepository.save(modelMapper.map(eventSaveRequest, Event.class));
        return modelMapper.map(event, EventResource.class);
    }

    @Override
    public EventResource updateEvent(int eventId, EventCreateOrUpdateRequest eventUpdateRequest) throws EventNotFoundException {
        if(!this.eventRepository.existsById(eventId)) throw new EventNotFoundException(eventId);
        Event event = modelMapper.map(eventUpdateRequest, Event.class);
        event.setId(eventId);
        return modelMapper.map(eventRepository.save(event), EventResource.class);
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
    public Page<EventResource> findAllFutureEvents(
            String location,
            Integer excludeParticipantId,
            int pageSize,
            int pageNumber
    ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return eventRepository.findAllFutureEvents(location, excludeParticipantId, pageable)
                .map(event -> modelMapper.map(event, EventResource.class));
    }

    @Override
    public Page<ParticipantResource> findAllParticipantsForEvent(int eventId, int pageSize, int pageNumber) throws EventNotFoundException {
        if(!this.eventRepository.existsById(eventId)) throw new EventNotFoundException(eventId);
        return this.participantRepository.findAllByEventsId(eventId, PageRequest.of(pageNumber,pageSize))
                .map(participant -> modelMapper.map(participant, ParticipantResource.class));
    }
}
