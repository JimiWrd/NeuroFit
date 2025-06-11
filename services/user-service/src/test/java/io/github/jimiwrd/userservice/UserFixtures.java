package io.github.jimiwrd.userservice;

import io.github.jimiwrd.userservice.user.Role;
import io.github.jimiwrd.userservice.user.User;
import io.github.jimiwrd.userservice.user.request.CreateUserRequest;

import java.time.Instant;
import java.util.UUID;

public class UserFixtures {

    private UserFixtures(){}

    public static User generateUser() {
        return User.builder()
                .id(UUID.randomUUID())
                .firstName("Jimi")
                .lastName("Ward")
                .username("jimiWrd")
                .email("jimiWrd@email.com")
                .password("password")
                .role(Role.USER)
                .emailVerified(true)
                .created(Instant.now())
                .updated(Instant.now())
                .build();
    }

    public static User generateUser(UUID id,
                                    String firstName,
                                    String lastName,
                                    String userName,
                                    String email,
                                    String password,
                                    Role role,
                                    boolean emailVerified,
                                    Instant now) {
        return User.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .username(userName)
                .email(email)
                .password(password)
                .role(role)
                .emailVerified(emailVerified)
                .created(now)
                .updated(now)
                .build();
    }

    public static CreateUserRequest generateCreateUserRequest() {
        return CreateUserRequest.builder()
                .firstName("Jimi")
                .lastName("Ward")
                .username("jimiWrd")
                .email("jimiWrd@email.com")
                .password("password")
                .build();
    }

    public static CreateUserRequest generateCreateUserRequest(String firstName,
                                                              String lastName,
                                                              String userName,
                                                              String email,
                                                              String password) {
        return CreateUserRequest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .username(userName)
                .email(email)
                .password(password)
                .build();
    }
}
