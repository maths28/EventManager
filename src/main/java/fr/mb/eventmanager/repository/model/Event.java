package fr.mb.eventmanager.repository.model;

import fr.mb.eventmanager.controller.dto.EventResource;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String location;
    private int maxCapacity;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "events")
    private Set<Participant> participants;

    public Event(int id, String name, String description, LocalDateTime startDate, LocalDateTime endDate, String location, int maxCapacity, Set<Participant> participants) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.maxCapacity = maxCapacity;
        this.participants = participants;
    }

    public Event(String name, String description, LocalDateTime startDate, LocalDateTime endDate, String location, int maxCapacity) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.maxCapacity = maxCapacity;
        this.participants = new HashSet<>();
    }

    public Event() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Set<Participant> getParticipants() {
        return participants;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public EventResource toEventResource(){
        return new EventResource(this.id, this.name, this.description, this.startDate, this.endDate, this.location, this.maxCapacity);
    }
}
