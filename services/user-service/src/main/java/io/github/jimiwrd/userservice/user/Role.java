package io.github.jimiwrd.userservice.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    USER ("user");

    private final String fieldName;
}
