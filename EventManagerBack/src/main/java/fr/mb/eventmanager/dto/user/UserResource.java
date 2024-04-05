package fr.mb.eventmanager.dto.user;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.mb.eventmanager.dto.participant.ParticipantResource;
import lombok.Data;

@Data
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "role",
        include = JsonTypeInfo.As.EXISTING_PROPERTY
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = UserResource.class, name = "ORGA"),
        @JsonSubTypes.Type(value = ParticipantResource.class, name = "PARTICIPANT")
})
public class UserResource {
    private int id;
    private String email;
    private String role;
}
