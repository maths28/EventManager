package fr.mb.eventmanager.controller.dto;

import fr.mb.eventmanager.repository.model.Event;
import fr.mb.eventmanager.repository.model.Participant;

public record ParticipantCreateRequest(String firstName, String lastName, String email){
    public Participant toParticipant(){
        return new Participant(this.firstName, this.lastName, this.email);
    }
}
