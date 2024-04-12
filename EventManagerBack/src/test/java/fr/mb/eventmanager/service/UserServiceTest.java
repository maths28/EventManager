package fr.mb.eventmanager.service;

import fr.mb.eventmanager.data.UserData;
import fr.mb.eventmanager.dto.user.UserCreateRequest;
import fr.mb.eventmanager.dto.user.UserResource;
import fr.mb.eventmanager.exception.UserEmailAlreadyExistsException;
import fr.mb.eventmanager.mapper.UserMapper;
import fr.mb.eventmanager.model.User;
import fr.mb.eventmanager.repository.UserRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private IUserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Nested
    class CreateUserTest {

        @Test
        void createUser() throws UserEmailAlreadyExistsException {
            UserCreateRequest userCreateRequest = UserData.getUserCreateRequest();

            User convertedUserFromRequest = UserData.getUser();
            convertedUserFromRequest.setId(0);

            User userSaved = UserData.getUser();
            userSaved.setPassword("$H1D3nPaSS");

            UserResource userResource = UserData.getUserResource();

            when(userRepository.findByEmail(userCreateRequest.getEmail())).thenReturn(Optional.empty());
            when(userMapper.toUser(userCreateRequest)).thenReturn(convertedUserFromRequest);
            when(passwordEncoder.encode(convertedUserFromRequest.getPassword())).thenReturn("$H1D3nPaSS");
            when(userRepository.save(convertedUserFromRequest)).thenReturn(userSaved);
            when(userMapper.toUserResource(userSaved)).thenReturn(userResource);
            UserResource userResource1 = userService.createUser(userCreateRequest);
            assertEquals(userResource1, userResource);
        }

        @Test
        void emailAlreadyExists() {
            UserCreateRequest userCreateRequest = UserData.getUserCreateRequest();

            User userAlreadyExists = UserData.getUser();

            when(userRepository.findByEmail(userCreateRequest.getEmail())).thenReturn(Optional.of(userAlreadyExists));
            assertThrows(UserEmailAlreadyExistsException.class, () -> userService.createUser(userCreateRequest));
        }
    }


    @Nested
    class FindUserByEmailTest {

        @Test
        void emailExists(){
            User userAlreadyExists = UserData.getUser();
            UserResource convertedFoundUser = UserData.getUserResource();
            when(userRepository.findByEmail(userAlreadyExists.getEmail())).thenReturn(Optional.of(userAlreadyExists));
            when(userMapper.toUserResource(userAlreadyExists)).thenReturn(convertedFoundUser);
            assertEquals(convertedFoundUser, userService.findUserByEmail(userAlreadyExists.getEmail()));
        }

        @Test
        void emailNotExists(){
            when(userRepository.findByEmail("test")).thenReturn(Optional.empty());
            assertNull(userService.findUserByEmail("test"));
        }

    }

    @Nested
    class ExistsUserByEmailTest {

        @Test
        void emailExistsAndNotExists(){
            when(userRepository.existsByEmail("test@test.com")).thenReturn(true);
            when(userRepository.existsByEmail("test2@test.com")).thenReturn(false);
            assertTrue(userService.existUserByEmail("test@test.com"));
            assertFalse(userService.existUserByEmail("test2@test.com"));
        }
    }



}
