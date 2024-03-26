package fr.mb.eventmanager.repository;

import fr.mb.eventmanager.model.Participant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Integer> {
    Page<Participant> findAllByEventsId(int id, Pageable pageable);
}
