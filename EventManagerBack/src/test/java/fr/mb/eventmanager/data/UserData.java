package fr.mb.eventmanager.data;

import fr.mb.eventmanager.dto.user.UserCreateRequest;
import fr.mb.eventmanager.dto.user.UserResource;
import fr.mb.eventmanager.model.User;

public class UserData {

    public static UserCreateRequest getUserCreateRequest() {
        UserCreateRequest userCreateRequest = new UserCreateRequest();
        userCreateRequest.setEmail("orga@test.com");
        userCreateRequest.setPassword("12345");
        userCreateRequest.setRole("ORGA");
        return userCreateRequest;
    }

    public static UserResource getUserResource() {
        UserResource userResource = new UserResource();
        userResource.setId(1);
        userResource.setEmail("orga@test.com");
        userResource.setRole("ORGA");
        return userResource;
    }

    public static User getUser() {
        User user = new User();
        user.setId(1);
        user.setEmail("orga@test.com");
        user.setPassword("12345");
        user.setRole("ORGA");
        return user;
    }

    private UserData(){}

}
