package io.github.jimiwrd.userservice.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.jimiwrd.userservice.error.ErrorCode;
import io.github.jimiwrd.userservice.error.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        System.out.printf("Unauthorised access to %s: %s (%s)%n", request.getRequestURI(), authException.getMessage(), authException.getClass().getSimpleName());

        if (authException.getCause() != null) {
            System.out.printf("Cause: %s%n", authException.getCause());
        }
        authException.printStackTrace();

        String authHeader = request.getHeader("Authorization");
        System.out.printf("Authorization header: %s%n", authHeader);

        ErrorResponse errorResponse = new ErrorResponse("Invalid or expired JWT", ErrorCode.UNAUTHORISED);
        String responseString = objectMapper.writeValueAsString(errorResponse);

        response.getWriter().write(responseString);
    }
}
