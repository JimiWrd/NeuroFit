package io.github.jimiwrd.userservice.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @NonNull
    private UUID id;

    @NonNull
    private UUID keycloakId;

    @NonNull
    private String firstName;

    private String lastName;

    private String username;

    @NonNull
    private String email;

    @NonNull
    private Role role;

    @NonNull
    private Instant created;

    @NonNull
    private Instant updated;
}
