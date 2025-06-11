package io.github.jimiwrd.userservice.unit;

import io.github.jimiwrd.userservice.UserFixtures;
import io.github.jimiwrd.userservice.user.User;
import io.github.jimiwrd.userservice.user.UserRepository;
import io.github.jimiwrd.userservice.user.UserService;
import io.github.jimiwrd.userservice.user.request.CreateUserRequest;
import io.github.jimiwrd.userservice.user.response.UserResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTests {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    void createUser_shouldReturnUserResponse_whenValidRequest() {
        CreateUserRequest request = UserFixtures.generateCreateUserRequest();

        when(userRepository.save(any(User.class))).thenReturn(UserFixtures.generateUser());

        UserResponse response = userService.createUser(request);

        assertThat(response)
                .extracting("firstName", "lastName", "username", "email", "role")
                .containsExactly(response.firstName(), response.lastName(), response.username(), response.email(), response.role());
    }

    @Test
    void createUser_shouldThrowException_whenUserEmailExists() {
        CreateUserRequest request = UserFixtures.generateCreateUserRequest();

        when(userRepository.existsByEmail(request.email())).thenReturn(true);

        assertThatThrownBy(() -> userService.createUser(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(String.format("User with email %s already exists", request.email()));
    }

}
