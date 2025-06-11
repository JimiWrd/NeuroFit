package io.github.jimiwrd.userservice.user;

import io.github.jimiwrd.userservice.user.request.CreateUserRequest;
import io.github.jimiwrd.userservice.user.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse createUser(CreateUserRequest request) {

        if(userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException(String.format("User with email %s already exists", request.email()));
        }

        User user = User.builder()
                .id(UUID.randomUUID())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .username(request.username())
                .email(request.email())
                .password(request.password())
                .role(Role.USER)
                .emailVerified(false)
                .created(Instant.now())
                .updated(Instant.now())
                .build();

        userRepository.save(user);

        return UserResponse.fromUser(user);
    }
}
