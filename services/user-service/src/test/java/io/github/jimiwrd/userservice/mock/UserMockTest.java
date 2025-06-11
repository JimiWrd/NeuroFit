package io.github.jimiwrd.userservice.mock;

import io.github.jimiwrd.userservice.BaseMockTest;
import io.github.jimiwrd.userservice.UserFixtures;
import io.github.jimiwrd.userservice.user.Role;
import io.github.jimiwrd.userservice.user.request.CreateUserRequest;
import io.github.jimiwrd.userservice.user.response.UserResponse;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("unchecked")
public class UserMockTest extends BaseMockTest {

    @Test
    void createUser_shouldReturnUserResponse_whenValidRequest() {
        CreateUserRequest request = UserFixtures.generateCreateUserRequest();

        UserResponse response = (UserResponse) createUser(request, 200);

        assertThat(response).extracting("firstName", "lastName", "username", "email", "role", "emailVerified").containsExactly(request.firstName(), request.lastName(), request.username(), request.email(), "USER", false);
    }

}
