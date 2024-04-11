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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements IUserService {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;

    @Override
    public UserResource createUser(UserCreateRequest userCreateRequest) throws UserEmailAlreadyExistsException{
        if(userRepository.findByEmail(userCreateRequest.getEmail()).isPresent())
            throw new UserEmailAlreadyExistsException(userCreateRequest.getEmail());
        User user = userMapper.toUser(userCreateRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.toUserResource(this.userRepository.save(user));
    }

    @Override
    public UserResource findUserByEmail(String email) {
        return this.userRepository.findByEmail(email).map(userMapper::toUserResource)
                .orElse(null);
    }

    @Override
    public Boolean existUserByEmail(String email) {
        return this.userRepository.existsByEmail(email);
    }
}

