package fr.mb.eventmanager.service.authprovider;

import fr.mb.eventmanager.model.User;
import fr.mb.eventmanager.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EventManagerAuthProvider implements AuthenticationProvider {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        User user = this.userRepository.findByEmail(username)
                .orElseThrow(()->new BadCredentialsException("No user found with email " + authentication.getName()));
        if(passwordEncoder.matches(password, user.getPassword())){
            return new UsernamePasswordAuthenticationToken(
                    username,
                    password,
                    List.of(new SimpleGrantedAuthority(user.getRole()))
            );
        } else {
            throw new BadCredentialsException("Wrong password");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
