package fr.mb.eventmanager.controller;

import fr.mb.eventmanager.dto.event.EventResource;
import fr.mb.eventmanager.dto.participant.ParticipantCreateRequest;
import fr.mb.eventmanager.dto.participant.ParticipantResource;
import fr.mb.eventmanager.exception.*;
import fr.mb.eventmanager.service.IParticipantService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/participants")
@CrossOrigin("http://localhost:4200")
public class ParticipantController {

    private final IParticipantService participantService;

    public ParticipantController(IParticipantService participantService) {
        this.participantService = participantService;
    }

    @GetMapping("/search")
    public ParticipantResource findParticipantByEmail(@RequestParam String email){
        return this.participantService.findParticipantByEmail(email);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipantResource createParticipant(@RequestBody @Valid ParticipantCreateRequest participantCreateRequest)
        throws ParticipantEmailAlreadyExistsException {
            return participantService.createParticipant(participantCreateRequest);
    }

    @PostMapping("/{participantId}/events/{eventId}")
    public List<EventResource> registerParticipantToEvent(@PathVariable int participantId, @PathVariable int eventId
    ) throws EventNotFoundException, ParticipantNotFoundException, EventFullException {
        return this.participantService.registerParticipantToEvent(participantId, eventId);
    }

    @GetMapping("/{id}/events")
    public Page<EventResource> findAllEventsForParticipant(
            @PathVariable("id") int participantId,
            @RequestParam int pageSize,
            @RequestParam int pageNumber
    ) throws ParticipantNotFoundException {
        return this.participantService.findAllEventsForParticipant(participantId, pageSize, pageNumber);
    }

    @DeleteMapping("/{participantId}/events/{eventId}")
    public List<EventResource> unregisterParticipantToEvent(@PathVariable int participantId,
                                                          @PathVariable int eventId
    ) throws UserNotRegisteredForEventException, ParticipantNotFoundException {
        return this.participantService.unregisterParticipantToEvent(participantId, eventId);
    }
}
