package io.github.jimiwrd.userservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.jimiwrd.userservice.user.User;
import io.github.jimiwrd.userservice.user.request.CreateUserRequest;
import io.github.jimiwrd.userservice.user.response.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;

import static org.assertj.core.api.Fail.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = UserServiceApplication.class)
@ContextConfiguration
@AutoConfigureMockMvc
public class BaseMockTest {

    static PostgreSQLContainer<?> POSTGRES_CONTAINER = new PostgreSQLContainer<>("postgres:latest");

    static {
        POSTGRES_CONTAINER.start();
    }

    @Autowired
    protected MockMvc mvc;

    @Autowired
    protected ObjectMapper mapper;

    @Autowired
    private List<JpaRepository<?, ?>> repositories;

    @DynamicPropertySource
    static void containerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", () -> POSTGRES_CONTAINER.getJdbcUrl() + "/organisation-service");;
    }

    @BeforeEach
    void clearDB() {
        repositories.forEach(JpaRepository::deleteAll);
    }

    public Object createUser(CreateUserRequest request, int expectedStatusCode) {
        try {
            ResultActions result = mvc.perform(MockMvcRequestBuilders
                    .post("/users")
                    .content(mapper.writeValueAsBytes(request))
                    .contentType(MediaType.APPLICATION_JSON));

            MockHttpServletResponse response = result.andReturn().getResponse();

            if(expectedStatusCode == 404) {
                result.andExpect(status().is4xxClientError());
                return mapper.readValue(response.getContentAsString(), ErrorResponse.class);
            }

            result.andExpect(status().isCreated());
            return mapper.readValue(response.getContentAsString(), UserResponse.class);

        } catch (Exception e) {
            fail(String.format("Create user failed, error: %s", e.getMessage()));
        }

        throw new RuntimeException("createUser() failed");
    }

}
