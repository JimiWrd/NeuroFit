package io.github.jimiwrd.userservice.unit;

import io.github.jimiwrd.userservice.UserFixtures;
import io.github.jimiwrd.userservice.user.Role;
import io.github.jimiwrd.userservice.user.User;
import io.github.jimiwrd.userservice.user.UserRepository;
import io.github.jimiwrd.userservice.user.UserService;
import io.github.jimiwrd.userservice.user.request.FindOrCreateRequest;
import io.github.jimiwrd.userservice.user.response.UserResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTests {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    void findOrCreateUser_shouldReturnUserResponse_whenCreateUser() {
        FindOrCreateRequest request = UserFixtures.generateCreateUserRequest();

        when(userRepository.save(any(User.class))).thenReturn(UserFixtures.generateUser());

        UserResponse response = userService.findOrCreateUser(request);

        assertThat(response)
                .extracting("keycloakId", "firstName", "lastName", "username", "email")
                .containsExactly(request.keycloakId(), request.firstName(), request.lastName(), request.username(), request.email());
        assertThat(response.role()).isEqualTo(Role.USER);
    }

    @Test
    void findOrCreateUser_shouldReturnUserResponse_whenFindUser() {
        FindOrCreateRequest request = UserFixtures.generateCreateUserRequest();

        when(userRepository.save(any(User.class))).thenReturn(UserFixtures.generateUser());

        UserResponse response = userService.findOrCreateUser(request);

        assertThat(response)
                .extracting("keycloakId", "firstName", "lastName", "username", "email")
                .containsExactly(request.keycloakId(), request.firstName(), request.lastName(), request.username(), request.email());
        assertThat(response.role()).isEqualTo(Role.USER);
    }

}
