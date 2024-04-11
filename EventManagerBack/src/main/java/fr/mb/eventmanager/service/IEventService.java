package fr.mb.eventmanager.service;

import fr.mb.eventmanager.dto.event.EventCreateOrUpdateRequest;
import fr.mb.eventmanager.dto.event.EventResource;
import fr.mb.eventmanager.dto.participant.ParticipantResource;
import fr.mb.eventmanager.exception.EventNotFoundException;
import fr.mb.eventmanager.exception.MaxCapacitySmallerThanTotalPartException;
import org.springframework.data.domain.Page;

public interface IEventService {

    EventResource getEvent(int eventId) throws EventNotFoundException;

    public EventResource saveEvent(EventCreateOrUpdateRequest eventSaveRequest);

    public EventResource updateEvent(int eventId, EventCreateOrUpdateRequest eventUpdateRequest) throws EventNotFoundException, MaxCapacitySmallerThanTotalPartException;

    public void deleteEvent(int eventId) throws EventNotFoundException;

    public Page<EventResource> findAllFutureEvents(
            String location, Integer excludeParticipantId, int pageSize, int pageNumber);

    public Page<ParticipantResource> findAllParticipantsForEvent(int eventId, int pageSize, int pageNumber) throws EventNotFoundException;


}
