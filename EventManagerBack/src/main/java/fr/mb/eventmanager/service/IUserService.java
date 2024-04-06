package fr.mb.eventmanager.service;

import fr.mb.eventmanager.dto.user.UserCreateRequest;
import fr.mb.eventmanager.dto.user.UserResource;
import fr.mb.eventmanager.exception.UserEmailAlreadyExistsException;

public interface IUserService {

    UserResource createUser(UserCreateRequest userCreateRequest) throws UserEmailAlreadyExistsException;

    public UserResource findParticipantByEmail(String email);
}
