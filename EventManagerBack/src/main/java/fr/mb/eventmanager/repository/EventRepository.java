package fr.mb.eventmanager.repository;

import fr.mb.eventmanager.model.Event;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {

    @Query("from Event e where e.startDate >= current_time and (:location is null or e.location = :location) ")
    public List<Event> findAllFutureEvents(String location, Pageable pageable);

}
