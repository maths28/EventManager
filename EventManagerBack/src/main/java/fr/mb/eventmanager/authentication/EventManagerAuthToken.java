package fr.mb.eventmanager.authentication;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@EqualsAndHashCode(callSuper = true)
public class EventManagerAuthToken extends UsernamePasswordAuthenticationToken {

    private final int id;

    public EventManagerAuthToken(
            int id,
            String email,
            String password,
            Collection<? extends GrantedAuthority> authorities
    ) {
        super(email, password, authorities);
        this.id = id;
    }
}
