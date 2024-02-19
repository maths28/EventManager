package fr.mb.eventmanager.controller.dto;

import fr.mb.eventmanager.repository.model.Event;

import java.time.LocalDateTime;

public record EventCreateOrUpdateRequest(String name, String description, LocalDateTime startDate, LocalDateTime endDate, String location, int maxCapacity) {
    public Event toEvent(){
        return new Event(this.name, this.description, this.startDate, this.endDate, this.location, this.maxCapacity);
    }
}
