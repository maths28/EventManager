package fr.mb.eventmanager.service;

import fr.mb.eventmanager.controller.dto.EventResource;
import fr.mb.eventmanager.controller.dto.EventCreateOrUpdateRequest;
import fr.mb.eventmanager.controller.dto.ParticipantResource;
import fr.mb.eventmanager.exception.EventNotFoundException;
import fr.mb.eventmanager.exception.ParticipantNotFoundException;

import java.util.List;

public interface IEventService {

    public EventResource saveEvent(EventCreateOrUpdateRequest eventSaveRequest);

    public EventResource updateEvent(int eventId, EventCreateOrUpdateRequest eventUpdateRequest) throws EventNotFoundException;

    public void deleteEvent(int eventId) throws EventNotFoundException;

    public List<EventResource> findAllFutureEvents(String location, int pageSize, int pageNumber);

    public List<ParticipantResource> findAllParticipantsForEvent(int eventId) throws EventNotFoundException;


}
