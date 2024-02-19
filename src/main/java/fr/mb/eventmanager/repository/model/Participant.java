package fr.mb.eventmanager.repository.model;

import fr.mb.eventmanager.controller.dto.ParticipantResource;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;

    private String lastName;

    private String email;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Event> events;

    public Participant(int id, String firstName, String lastName, String email, Set<Event> events) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.events = events;
    }

    public Participant(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.events = new HashSet<>();
    }

    public Participant() {

    }

    public Set<Event> getEvents() {
        return events;
    }

    public void addEvent(Event event){
        this.events.add(event);
    }

    public boolean removeEvent(int eventId){
        return this.events.removeIf(event -> event.getId() == eventId);
    }

    public ParticipantResource toParticipantResource(){
        return new ParticipantResource(this.id, this.firstName, this.lastName, this.email);
    }
}
