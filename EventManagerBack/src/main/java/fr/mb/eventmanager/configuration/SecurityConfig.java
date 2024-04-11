package fr.mb.eventmanager.configuration;

import fr.mb.eventmanager.authorization.ParticipantAuthorizationManager;
import fr.mb.eventmanager.filter.CsrfCookieFilter;
import fr.mb.eventmanager.filter.JwtTokenGeneratorFilter;
import fr.mb.eventmanager.filter.JwtTokenValidatorFilter;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtTokenGeneratorFilter jwtTokenGeneratorFilter,
            JwtTokenValidatorFilter jwtTokenValidatorFilter
    ) throws Exception {
        return http
            .sessionManagement((session)->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers(headers-> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
            .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                    config.setAllowedMethods(Collections.singletonList("*"));
                    config.setAllowCredentials(true);
                    config.setAllowedHeaders(Collections.singletonList("*"));
                    config.setExposedHeaders(List.of("Authorization"));
                    config.setMaxAge(3600L);
                    return config;
                }))
           .csrf((csrf)-> csrf
                   .ignoringRequestMatchers("/user")
                   .ignoringRequestMatchers(PathRequest.toH2Console())
                   .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
                   .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
           )
           .addFilterAfter(new CsrfCookieFilter(), SessionManagementFilter.class)
           .addFilterAfter(jwtTokenGeneratorFilter, BasicAuthenticationFilter.class)
           .addFilterBefore(jwtTokenValidatorFilter, BasicAuthenticationFilter.class)
           .authorizeHttpRequests(httpRequest -> httpRequest
                   .requestMatchers("/user/**").permitAll()
                   .requestMatchers("/login").authenticated()
                   .requestMatchers("/participants/{id}/**").access(new ParticipantAuthorizationManager())
                   .requestMatchers(HttpMethod.GET, "/events").authenticated()
                   .requestMatchers("/events/**").hasRole("ORGA")
                   .requestMatchers(PathRequest.toH2Console()).permitAll()
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
