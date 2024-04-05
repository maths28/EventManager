package fr.mb.eventmanager.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("P")
public class Participant extends User {
    private String firstName;
    private String lastName;
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
