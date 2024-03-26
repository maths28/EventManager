package fr.mb.eventmanager.model;

import fr.mb.eventmanager.dto.event.EventResource;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.HashSet;
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

}
