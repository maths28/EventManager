package fr.mb.eventmanager.controller;

import fr.mb.eventmanager.dto.event.EventResource;
import fr.mb.eventmanager.dto.event.EventCreateOrUpdateRequest;
import fr.mb.eventmanager.dto.participant.ParticipantResource;
import fr.mb.eventmanager.exception.EventNotFoundException;
import fr.mb.eventmanager.exception.MaxCapacitySmallerThanTotalPartException;
import fr.mb.eventmanager.service.IEventService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final IEventService eventService;

    public EventController(IEventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/{id}")
    public EventResource getEvent(@PathVariable("id") int eventId) throws EventNotFoundException{
        return this.eventService.getEvent(eventId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventResource createEvent(@RequestBody @Valid EventCreateOrUpdateRequest eventCreateRequest){
        return eventService.saveEvent(eventCreateRequest);
    }

    @PutMapping("/{id}")
    public EventResource updateEvent(
            @PathVariable("id") int eventId,
            @RequestBody @Valid EventCreateOrUpdateRequest eventUpdateRequest
    ) throws EventNotFoundException, MaxCapacitySmallerThanTotalPartException {
        return eventService.updateEvent(eventId, eventUpdateRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelEvent(@PathVariable("id") int eventId) throws EventNotFoundException{
        eventService.deleteEvent(eventId);
    }

    @GetMapping
    @PreAuthorize(""" 
        (#excludeParticipantId != null && hasRole("PARTICIPANT") && #excludeParticipantId == authentication.id) ||
        (#excludeParticipantId == null && hasRole("ORGA"))
    """)
    public Page<EventResource> findAllFuturesEvents(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Integer excludeParticipantId,
            @RequestParam int pageSize,
            @RequestParam int pageNumber){
        return eventService.findAllFutureEvents(location, excludeParticipantId, pageSize, pageNumber);
    }

    @GetMapping("/{id}/participants")
    public Page<ParticipantResource> findAllParticipantsForEvent(
            @PathVariable("id") int eventId,
            @RequestParam int pageSize,
            @RequestParam int pageNumber
    ) throws EventNotFoundException {
        return this.eventService.findAllParticipantsForEvent(eventId, pageSize, pageNumber);
    }

}
