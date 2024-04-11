package fr.mb.eventmanager.controller;

import fr.mb.eventmanager.dto.user.UserCreateRequest;
import fr.mb.eventmanager.dto.user.UserResource;
import fr.mb.eventmanager.exception.UserEmailAlreadyExistsException;
import fr.mb.eventmanager.service.IUserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class UserController {

    private final IUserService userService;

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResource createUser(@RequestBody @Valid UserCreateRequest userCreateRequest)
            throws UserEmailAlreadyExistsException {
        return userService.createUser(userCreateRequest);
    }

    @GetMapping("/user/search")
    public Boolean existUserByEmail(@RequestParam String email){
        return this.userService.existUserByEmail(email);
    }

    @GetMapping("/login")
    public UserResource login(Authentication authentication){
        return userService.findUserByEmail(authentication.getName());
    }
}
