package fr.mb.eventmanager.dto.user;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.mb.eventmanager.dto.participant.ParticipantCreateRequest;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "role",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = UserCreateRequest.class, name = "ORGA"),
        @JsonSubTypes.Type(value = ParticipantCreateRequest.class, name = "PARTICIPANT")
})
@Data
public class UserCreateRequest {
    @NotEmpty(message = "Le mail est obligatoire")
    private String email;
    @NotEmpty(message = "Le mot de passe est obligatoire")
    private String password;
    @NotEmpty(message = "Le role est obligatoire")
    private String role;
}
