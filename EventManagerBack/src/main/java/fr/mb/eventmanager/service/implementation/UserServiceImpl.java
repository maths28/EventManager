package fr.mb.eventmanager.service.implementation;

import fr.mb.eventmanager.dto.participant.ParticipantResource;
import fr.mb.eventmanager.dto.user.UserCreateRequest;
import fr.mb.eventmanager.dto.user.UserResource;
import fr.mb.eventmanager.exception.UserEmailAlreadyExistsException;
import fr.mb.eventmanager.mapper.UserMapper;
import fr.mb.eventmanager.model.User;
import fr.mb.eventmanager.repository.UserRepository;
import fr.mb.eventmanager.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements IUserService {

    private UserRepository userRepository;
    private UserMapper userMapper;

    @Override
    public UserResource createUser(UserCreateRequest userCreateRequest) throws UserEmailAlreadyExistsException{
        if(userRepository.findByEmail(userCreateRequest.getEmail()).isPresent())
            throw new UserEmailAlreadyExistsException(userCreateRequest.getEmail());
        User user = userMapper.toUser(userCreateRequest);
        return userMapper.toUserResource(this.userRepository.save(user));
    }

    @Override
    public UserResource findParticipantByEmail(String email) {
        return this.userRepository.findByEmail(email).map(userMapper::toUserResource)
                .orElse(null);
    }
}

