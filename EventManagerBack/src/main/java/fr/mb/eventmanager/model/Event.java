package fr.mb.eventmanager.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Formula;

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
    private int maxCapacity;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "events")
    @EqualsAndHashCode.Exclude
    private Set<Participant> participants;
    @Formula("(SELECT COUNT(*) FROM participant_events WHERE participant_events.events_id = id)")
    private int totalParticipants;
}
