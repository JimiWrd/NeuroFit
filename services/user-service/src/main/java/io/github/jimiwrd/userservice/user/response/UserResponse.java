package io.github.jimiwrd.userservice.user.response;

import io.github.jimiwrd.userservice.user.Role;
import io.github.jimiwrd.userservice.user.User;

import java.util.UUID;

public record UserResponse(UUID id,
                           UUID keycloakId,
                           String firstName,
                           String lastName,
                           String username,
                           String email,
                           Role role) {

    public static UserResponse fromUser(User user) {
        return new UserResponse(
                user.getId(),
                user.getKeycloakId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail(),
                user.getRole()
        );
    }
}
