package io.github.jimiwrd.userservice.user;

import io.github.jimiwrd.userservice.user.request.FindOrCreateRequest;
import io.github.jimiwrd.userservice.user.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> findOrCreateUser(@AuthenticationPrincipal Jwt jwt) {
        FindOrCreateRequest request = FindOrCreateRequest.builder()
                .keycloakId(UUID.fromString(jwt.getSubject()))
                .firstName(jwt.getClaimAsString("given_name"))
                .lastName(jwt.getClaimAsString("family_name"))
                .username(jwt.getClaimAsString("preferred_username"))
                .email(jwt.getClaimAsString("email"))
                .role(Role.USER) // TODO assign role to user from JWT via keycloak
                .build();

        UserResponse response = userService.findOrCreateUser(request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
