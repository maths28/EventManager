package fr.mb.eventmanager.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Entity
@Data
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    @ManyToMany(fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    private Set<Event> events;

    public void addEvent(Event event){
        this.events.add(event);
    }

    public boolean removeEvent(int eventId){
        return this.events.removeIf(event -> event.getId() == eventId);
    }

}
