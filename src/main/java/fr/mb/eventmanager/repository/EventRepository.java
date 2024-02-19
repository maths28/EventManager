package fr.mb.eventmanager.repository;

import fr.mb.eventmanager.repository.model.Event;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends CrudRepository<Event, Integer> {

    @Query("from Event e where e.startDate >= current_time and (:location is null or e.location = :location) ")
    public List<Event> findAllFutureEvents(String location, Pageable pageable);

}
