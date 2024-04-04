package fr.mb.eventmanager.model;

import fr.mb.eventmanager.annotation.EndDateAfterStartDate;
import fr.mb.eventmanager.annotation.StartDateAfterNow;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.generator.EventType;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String location;
    private Integer maxCapacity;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "events")
    @EqualsAndHashCode.Exclude
    private Set<Participant> participants;
    @Formula("(SELECT COUNT(*) FROM participant_events WHERE participant_events.events_id = id)")
    @Generated(event ={EventType.INSERT, EventType.UPDATE})
    private int totalParticipants;
}
