package fr.mb.eventmanager.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
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
        return this.events.removeIf(event ->
                event.getId() == eventId &&
                        event.getStartDate().isAfter(LocalDateTime.now())
        );
    }

}
