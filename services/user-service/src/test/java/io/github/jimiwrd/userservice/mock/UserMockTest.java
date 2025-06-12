package io.github.jimiwrd.userservice.mock;

import io.github.jimiwrd.userservice.BaseMockTest;
import io.github.jimiwrd.userservice.UserFixtures;
import io.github.jimiwrd.userservice.error.ErrorCode;
import io.github.jimiwrd.userservice.error.ErrorResponse;
import io.github.jimiwrd.userservice.user.request.CreateUserRequest;
import io.github.jimiwrd.userservice.user.response.UserResponse;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SuppressWarnings("unchecked")
public class UserMockTest extends BaseMockTest {

    @Test
    void contextLoads() {
    }

    @Test
    void createUser_shouldReturnUserResponse_whenValidRequest() {
        CreateUserRequest request = UserFixtures.generateCreateUserRequest();

        UserResponse response = (UserResponse) createUser(request, 200);

        assertThat(response).extracting("firstName", "lastName", "username", "email", "role", "emailVerified").containsExactly(request.firstName(), request.lastName(), request.username(), request.email(), "USER", false);
    }

    @Test
    void createUser_shouldReturnErrorResponse_whenEmailExists() {
        CreateUserRequest request = UserFixtures.generateCreateUserRequest();

        createUser(request, 200);

        ErrorResponse response = (ErrorResponse) createUser(request, 404);

        assertThat(response).isInstanceOf(ErrorResponse.class);
        assertThat(response.errorCode()).isEqualTo(ErrorCode.BAD_REQUEST);
        assertThat(response.message()).isEqualTo("User with email %s already exists", request.email());
    }
}
