package fr.mb.eventmanager.service;

import fr.mb.eventmanager.dto.event.EventResource;
import fr.mb.eventmanager.dto.event.EventCreateOrUpdateRequest;
import fr.mb.eventmanager.dto.participant.ParticipantResource;
import fr.mb.eventmanager.exception.EventNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IEventService {

    EventResource getEvent(int eventId) throws EventNotFoundException;

    public EventResource saveEvent(EventCreateOrUpdateRequest eventSaveRequest);

    public EventResource updateEvent(int eventId, EventCreateOrUpdateRequest eventUpdateRequest) throws EventNotFoundException;

    public void deleteEvent(int eventId) throws EventNotFoundException;

    public Page<EventResource> findAllFutureEvents(String location, int pageSize, int pageNumber);

    public List<ParticipantResource> findAllParticipantsForEvent(int eventId) throws EventNotFoundException;


}
