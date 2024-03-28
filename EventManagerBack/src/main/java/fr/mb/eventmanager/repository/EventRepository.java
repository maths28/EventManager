package fr.mb.eventmanager.repository;

import fr.mb.eventmanager.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {

    @Query("""
        from Event e
        where e.startDate >= current_time
        and (:location is null or e.location = :location)
        and (
            :excludeParticipantId is null
            or not exists (select p from e.participants p where p.id = :excludeParticipantId)
            )
    """)
    public Page<Event> findAllFutureEvents(String location, Integer excludeParticipantId, Pageable pageable);

    Page<Event> findAllByParticipantsId(int id, Pageable pageable);

}
