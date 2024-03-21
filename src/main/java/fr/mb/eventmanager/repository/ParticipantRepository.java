package fr.mb.eventmanager.repository;

import fr.mb.eventmanager.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Integer> {

}
