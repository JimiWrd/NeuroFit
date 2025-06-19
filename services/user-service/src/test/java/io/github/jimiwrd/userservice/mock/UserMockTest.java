package io.github.jimiwrd.userservice.mock;

import io.github.jimiwrd.userservice.BaseMockTest;
import io.github.jimiwrd.userservice.user.response.UserResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

@SuppressWarnings("unchecked")
public class UserMockTest extends BaseMockTest {

    @Test
    void contextLoads() {
    }

    @Test
    void keycloak_shouldReturnTokenThenJWT_whenCreateAndLogIn() {
        try {
            String token = createKeycloakUser("user", "password", HttpStatus.CREATED);
            assertThat(token).isNotNull();

            Jwt jwt = loginKeycloakUser("user", "password");

            assertThat(jwt.getSubject()).isNotNull();
        } catch (Exception e) {
            fail("Should return JSON response with token, instead threw error:", e);
        }
    }

    @Test
    void findOrCreateUser_shouldCreateNewUserRecord_whenDoesNotExist() {
        try {
            String token = createKeycloakUser("user", "password", HttpStatus.CREATED);
            assertThat(token).isNotNull();

            Jwt jwt = loginKeycloakUser("user", "password");

            UserResponse response = (UserResponse) findOrCreateUser(jwt, HttpStatus.OK);

            assertThat(response).isNotNull();
            assertThat(response.keycloakId().toString()).isEqualTo(jwt.getSubject());
            assertThat(response.firstName()).isEqualTo("Test");
            assertThat(response.lastName()).isEqualTo("User");
            assertThat(response.username()).isEqualTo("user");
            assertThat(response.email()).isEqualTo("user@example.com");
            assertThat(response.role().getFieldName()).isEqualTo("user");

        } catch (Exception e) {
            fail("Should return JSON response with token, instead threw error:", e);
        }
    }
}
