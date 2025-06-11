package io.github.jimiwrd.userservice.user.request;

import io.github.jimiwrd.userservice.user.Role;
import lombok.Builder;

@Builder
public record CreateUserRequest(String firstName,
                                String lastName,
                                String username,
                                String email,
                                String password,
                                Role role) {

}
