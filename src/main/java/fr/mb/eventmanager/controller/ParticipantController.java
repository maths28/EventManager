package fr.mb.eventmanager.controller;

import fr.mb.eventmanager.controller.dto.EventResource;
import fr.mb.eventmanager.controller.dto.ParticipantCreateRequest;
import fr.mb.eventmanager.controller.dto.ParticipantResource;
import fr.mb.eventmanager.controller.dto.RegisterParticipantToEventRequest;
import fr.mb.eventmanager.exception.EventFullException;
import fr.mb.eventmanager.exception.EventNotFoundException;
import fr.mb.eventmanager.exception.ParticipantNotFoundException;
import fr.mb.eventmanager.exception.UserNotRegisteredForEventException;
import fr.mb.eventmanager.repository.model.Event;
import fr.mb.eventmanager.service.IParticipantService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/participants")
public class ParticipantController {

    private final IParticipantService participantService;

    public ParticipantController(IParticipantService participantService) {
        this.participantService = participantService;
    }

    @PostMapping
    public ParticipantResource createParticipant(@RequestBody ParticipantCreateRequest participantCreateRequest){
        return participantService.createParticipant(participantCreateRequest);
    }

    @PostMapping("/{id}/events")
    public List<EventResource> registerParticipantToEvent(@PathVariable("id") int participantId,
                                                          @RequestBody RegisterParticipantToEventRequest registerParticipantToEventRequest
    ) throws EventNotFoundException, ParticipantNotFoundException, EventFullException {
        return this.participantService.registerParticipantToEvent(participantId, registerParticipantToEventRequest);
    }

    @GetMapping("/{id}/events")
    public List<EventResource> findAllEventsForParticipant(@PathVariable("id") int participantId
    ) throws ParticipantNotFoundException {
        return this.participantService.findAllEventsForParticipant(participantId);
    }

    @DeleteMapping("/{participantId}/events/{eventId}")
    public List<EventResource> registerParticipantToEvent(@PathVariable int participantId,
                                                          @PathVariable int eventId
    ) throws UserNotRegisteredForEventException, ParticipantNotFoundException {
        return this.participantService.unregisterParticipantToEvent(participantId, eventId);
    }
}
