package io.github.jimiwrd.userservice.user;

import io.github.jimiwrd.userservice.user.request.FindOrCreateRequest;
import io.github.jimiwrd.userservice.user.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse findOrCreateUser(FindOrCreateRequest request) {
        Optional<User> found = userRepository.findByKeycloakId(request.keycloakId());

        if(found.isPresent()) {
            return UserResponse.fromUser(found.get());
        }

        User user = User.builder()
                .id(UUID.randomUUID())
                .keycloakId(request.keycloakId())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .username(request.username())
                .email(request.email())
                .role(request.role())
                .created(Instant.now())
                .updated(Instant.now())
                .build();

        User result = userRepository.save(user);

        return UserResponse.fromUser(result);
    }
}
