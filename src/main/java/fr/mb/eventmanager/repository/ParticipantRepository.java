package fr.mb.eventmanager.repository;

import fr.mb.eventmanager.repository.model.Event;
import fr.mb.eventmanager.repository.model.Participant;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ParticipantRepository extends CrudRepository<Participant, Integer> {

}
