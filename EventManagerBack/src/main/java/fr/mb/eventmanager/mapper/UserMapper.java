package fr.mb.eventmanager.mapper;

import fr.mb.eventmanager.dto.participant.ParticipantCreateRequest;
import fr.mb.eventmanager.dto.participant.ParticipantResource;
import fr.mb.eventmanager.dto.user.UserCreateRequest;
import fr.mb.eventmanager.dto.user.UserResource;
import fr.mb.eventmanager.model.Participant;
import fr.mb.eventmanager.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.SubclassMapping;

@Mapper(componentModel = "spring", uses = ParticipantMapper.class)
public interface UserMapper {
    @SubclassMapping(source = ParticipantCreateRequest.class, target = Participant.class)
    User toUser(UserCreateRequest userCreateRequest);

    @SubclassMapping(source = Participant.class, target = ParticipantResource.class)
    UserResource toUserResource(User user);
}
