package fr.mb.eventmanager.controller;

import fr.mb.eventmanager.dto.participant.ParticipantCreateRequest;
import fr.mb.eventmanager.dto.participant.ParticipantResource;
import fr.mb.eventmanager.dto.user.UserCreateRequest;
import fr.mb.eventmanager.dto.user.UserResource;
import fr.mb.eventmanager.exception.ParticipantEmailAlreadyExistsException;
import fr.mb.eventmanager.exception.UserEmailAlreadyExistsException;
import fr.mb.eventmanager.service.IUserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final IUserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResource createParticipant(@RequestBody @Valid UserCreateRequest userCreateRequest)
            throws UserEmailAlreadyExistsException {
        return userService.createUser(userCreateRequest);
    }

}
