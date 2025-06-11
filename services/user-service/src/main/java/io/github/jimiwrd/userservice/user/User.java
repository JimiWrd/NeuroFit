package io.github.jimiwrd.userservice.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@Builder
public class User {

    @Id
    @NonNull
    private UUID id;

    @NonNull
    private String firstName;

    private String lastName;

    @NonNull
    private String username;

    @NonNull
    private String email;

    @NonNull
    private String password;

    @NonNull
    private Role role;

    private boolean emailVerified;

    @NonNull
    private Instant created;

    @NonNull
    private Instant updated;
}
