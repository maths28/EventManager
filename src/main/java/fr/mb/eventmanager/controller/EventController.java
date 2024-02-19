package fr.mb.eventmanager.controller;

import fr.mb.eventmanager.controller.dto.EventResource;
import fr.mb.eventmanager.controller.dto.EventCreateOrUpdateRequest;
import fr.mb.eventmanager.controller.dto.ParticipantResource;
import fr.mb.eventmanager.exception.EventNotFoundException;
import fr.mb.eventmanager.exception.ParticipantNotFoundException;
import fr.mb.eventmanager.service.IEventService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final IEventService eventService;

    public EventController(IEventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public EventResource createEvent(@RequestBody EventCreateOrUpdateRequest eventCreateRequest){
        return eventService.saveEvent(eventCreateRequest);
    }

    @PutMapping("/{id}")
    public EventResource updateEvent(@PathVariable("id") int eventId, @RequestBody EventCreateOrUpdateRequest eventUpdateRequest) throws EventNotFoundException {
        return eventService.updateEvent(eventId, eventUpdateRequest);
    }

    @DeleteMapping("/{id}")
    public void cancelEvent(@PathVariable("id") int eventId) throws EventNotFoundException{
        eventService.deleteEvent(eventId);
    }

    @GetMapping
    public List<EventResource> findAllFuturesEvents(@RequestParam(required = false) String location, @RequestParam int pageSize, @RequestParam int pageNumber){
        return eventService.findAllFutureEvents(location, pageSize, pageNumber);
    }

    @GetMapping("/{id}/participants")
    public List<ParticipantResource> findAllEventsForParticipant(@PathVariable("id") int eventId
    ) throws EventNotFoundException {
        return this.eventService.findAllParticipantsForEvent(eventId);
    }

}
