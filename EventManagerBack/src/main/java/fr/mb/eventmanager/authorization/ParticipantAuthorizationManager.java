package fr.mb.eventmanager.authorization;

import fr.mb.eventmanager.authentication.EventManagerAuthToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import java.util.function.Supplier;

public class ParticipantAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        EventManagerAuthToken token =  (EventManagerAuthToken) authentication.get();
        String participantId = String.valueOf(token.getId());
        boolean hasRolePart = token.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_PARTICIPANT"));
        String requestedParticipantId = context.getVariables().get("id");
        return new AuthorizationDecision(hasRolePart && requestedParticipantId.equals(participantId));
    }
}
