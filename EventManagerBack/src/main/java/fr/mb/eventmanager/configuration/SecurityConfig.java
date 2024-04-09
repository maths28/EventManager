package fr.mb.eventmanager.configuration;

import fr.mb.eventmanager.authorization.ParticipantAuthorizationManager;
import fr.mb.eventmanager.filter.CsrfCookieFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .sessionManagement((session)->
                    session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
            .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                    config.setAllowedMethods(Collections.singletonList("*"));
                    config.setAllowCredentials(true);
                    config.setAllowedHeaders(Collections.singletonList("*"));
                    config.setMaxAge(3600L);
                    return config;
                }))
           .csrf((csrf)-> csrf
                   .ignoringRequestMatchers("/user")
                   .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
                   .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
           )
           .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
           .authorizeHttpRequests(httpRequest -> httpRequest
                   .requestMatchers("/user/**").permitAll()
                   .requestMatchers("/login").authenticated()
                   .requestMatchers("/participants/{id}/**").access(new ParticipantAuthorizationManager())
                   .requestMatchers(HttpMethod.GET, "/events").authenticated()
                   .requestMatchers("/events/**").hasRole("ORGA")
                   .anyRequest().authenticated()
           )
           .httpBasic(Customizer.withDefaults())
           .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
