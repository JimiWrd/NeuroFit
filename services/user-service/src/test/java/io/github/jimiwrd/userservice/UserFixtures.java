package io.github.jimiwrd.userservice;

import io.github.jimiwrd.userservice.user.Role;
import io.github.jimiwrd.userservice.user.User;
import io.github.jimiwrd.userservice.user.request.FindOrCreateRequest;

import java.time.Instant;
import java.util.UUID;

public class UserFixtures {

    private UserFixtures(){}

    public static User generateUser() {
        return User.builder()
                .id(UUID.randomUUID())
                .keycloakId(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"))
                .firstName("Jimi")
                .lastName("Ward")
                .username("jimiWrd")
                .email("jimiWrd@email.com")
                .role(Role.USER)
                .created(Instant.now())
                .updated(Instant.now())
                .build();
    }

    public static User generateUser(UUID id,
                                    UUID keycloakId,
                                    String firstName,
                                    String lastName,
                                    String userName,
                                    String email,
                                    Role role,
                                    Instant now) {
        return User.builder()
                .id(id)
                .keycloakId(keycloakId)
                .firstName(firstName)
                .lastName(lastName)
                .username(userName)
                .email(email)
                .role(role)
                .created(now)
                .updated(now)
                .build();
    }

    public static FindOrCreateRequest generateCreateUserRequest() {
        return FindOrCreateRequest.builder()
                .keycloakId(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"))
                .firstName("Jimi")
                .lastName("Ward")
                .username("jimiWrd")
                .email("jimiWrd@email.com")
                .role(Role.USER)
                .build();
    }

    public static FindOrCreateRequest generateCreateUserRequest(String firstName,
                                                                String lastName,
                                                                String userName,
                                                                String email,
                                                                Role role) {
        return FindOrCreateRequest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .username(userName)
                .email(email)
                .role(role)
                .build();
    }
}
