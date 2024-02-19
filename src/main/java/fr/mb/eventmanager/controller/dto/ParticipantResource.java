package fr.mb.eventmanager.controller.dto;

import fr.mb.eventmanager.repository.model.Participant;

public record ParticipantResource(int id, String firstName, String lastName, String email){
}
