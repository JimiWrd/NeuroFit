package io.github.jimiwrd.userservice.user.response;

import io.github.jimiwrd.userservice.user.User;
import lombok.Builder;

import java.util.UUID;

public record UserResponse(UUID id,
                           String firstName,
                           String lastName,
                           String username,
                           String email,
                           String role,
                           boolean emailVerified) {

    public static UserResponse fromUser(User user) {
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().name(),
                user.isEmailVerified()
        );
    }
}
