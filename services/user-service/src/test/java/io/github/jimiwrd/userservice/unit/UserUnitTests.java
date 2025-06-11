package io.github.jimiwrd.userservice.unit;

import io.github.jimiwrd.userservice.user.Role;
import io.github.jimiwrd.userservice.user.User;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UserUnitTests {

    @Test
    void User_shouldBuild_withAllValidFields() {
        User user = User.builder()
                .id(UUID.randomUUID())
                .firstName("Jimi")
                .lastName("Ward")
                .username("jimiWrd")
                .email("jimiWrd@email.com")
                .password("password")
                .role(Role.USER)
                .emailVerified(true)
                .created(Instant.now())
                .updated(Instant.now())
                .build();

        assertThat(user).isNotNull();
    }

    @Test
    void User_shouldNotBuild_withNullValues() {
        assertThatThrownBy(() -> User.builder()
                .id(null)
                .firstName("Jimi")
                .lastName("Ward")
                .username("jimiWrd")
                .email("jimiWrd@email.com")
                .password("password")
                .role(Role.USER)
                .emailVerified(true)
                .created(Instant.now())
                .updated(Instant.now())
                .build())
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("id");
    }


}
