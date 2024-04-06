package fr.mb.eventmanager.mapper;

import fr.mb.eventmanager.dto.event.EventCreateOrUpdateRequest;
import fr.mb.eventmanager.dto.event.EventResource;
import fr.mb.eventmanager.model.Event;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventMapper {
    EventResource toEventResource(Event event);
    Event toEvent(EventCreateOrUpdateRequest eventCreateOrUpdateRequest);
}
