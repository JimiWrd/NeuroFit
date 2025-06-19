package io.github.jimiwrd.userservice.user.request;

import io.github.jimiwrd.userservice.user.Role;
import lombok.Builder;

import java.util.UUID;

@Builder
public record FindOrCreateRequest(UUID keycloakId,
                                  String firstName,
                                  String lastName,
                                  String username,
                                  String email,
                                  Role role) {

}
