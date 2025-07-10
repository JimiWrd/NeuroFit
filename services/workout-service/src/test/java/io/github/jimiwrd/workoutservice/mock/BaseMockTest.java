package io.github.jimiwrd.workoutservice.mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.jimiwrd.workoutservice.WorkoutServiceApplication;
import io.github.jimiwrd.workoutservice.error.ErrorResponse;
import io.github.jimiwrd.workoutservice.exercise.request.ExerciseCreateRequest;
import io.github.jimiwrd.workoutservice.exercise.request.ExerciseUpdateRequest;
import io.github.jimiwrd.workoutservice.exercise.response.ExerciseResponse;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Fail.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = WorkoutServiceApplication.class)
@ContextConfiguration
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BaseMockTest {

    static MongoDBContainer MONGO_CONTAINER = new MongoDBContainer("mongo:latest").withExposedPorts(27017).withReuse(true);

    static {
        MONGO_CONTAINER.start();
    }

    @Autowired
    protected MockMvc mvc;

    @Autowired
    protected ObjectMapper mapper;

    @Autowired
    private List<MongoRepository<?, ?>> repositories;

    @DynamicPropertySource
    static void containerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", MONGO_CONTAINER::getReplicaSetUrl);
        registry.add("spring.data.mongodb.host", MONGO_CONTAINER::getHost);
        registry.add("spring,data.mongodb.port", MONGO_CONTAINER::getFirstMappedPort);
    }

    @BeforeEach
    void clearRepositories() {
        repositories.forEach(MongoRepository::deleteAll);
    }

    protected Object createExercise(ExerciseCreateRequest request, int expectedStatusCode) {
        try{
            ResultActions result = mvc.perform(MockMvcRequestBuilders
                    .post("/exercise")
                    .content(mapper.writeValueAsBytes(request))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(expectedStatusCode));

            MockHttpServletResponse response = result.andReturn().getResponse();

            return switch (expectedStatusCode) {
                case 200 -> mapper.readValue(response.getContentAsString(), ExerciseResponse.class);
                case 400 -> mapper.readValue(response.getContentAsString(), ErrorResponse.class);
                default -> throw new IllegalStateException("Unexpected value: " + expectedStatusCode);
            };

        } catch (Exception e) {
            fail(String.format("createExercise failed, error: %s", e.getMessage()));
        }

        throw new RuntimeException("createExercise() failed.");
    }

    protected Object getExercise(UUID id, int expectedStatusCode) {
        try{
            ResultActions result = mvc.perform(MockMvcRequestBuilders
                            .get("/exercise/{id}", id)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(expectedStatusCode));

            MockHttpServletResponse response = result.andReturn().getResponse();

            return switch (expectedStatusCode) {
                case 200 -> mapper.readValue(response.getContentAsString(), ExerciseResponse.class);
                case 400 -> mapper.readValue(response.getContentAsString(), ErrorResponse.class);
                default -> throw new IllegalStateException("Unexpected value: " + expectedStatusCode);
            };

        } catch (Exception e) {
            fail(String.format("getExercise failed, error: %s", e.getMessage()));
        }

        throw new RuntimeException("getExercise() failed.");
    }

    protected Object updateExercise(UUID id, ExerciseUpdateRequest request, int expectedStatusCode) {
        try{
            ResultActions result = mvc.perform(MockMvcRequestBuilders
                            .put("/exercise/{id}", id)
                            .content(mapper.writeValueAsBytes(request))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(expectedStatusCode));

            MockHttpServletResponse response = result.andReturn().getResponse();

            return switch (expectedStatusCode) {
                case 200 -> mapper.readValue(response.getContentAsString(), ExerciseResponse.class);
                case 400 -> mapper.readValue(response.getContentAsString(), ErrorResponse.class);
                default -> throw new IllegalStateException("Unexpected value: " + expectedStatusCode);
            };

        } catch (Exception e) {
            fail(String.format("updateExercise failed, error: %s", e.getMessage()));
        }

        throw new RuntimeException("updateExercise() failed.");
    }

    protected Object deleteExercise(UUID id, int expectedStatusCode) {
        try {
            ResultActions result = mvc.perform(MockMvcRequestBuilders
                    .delete("/exercise/{id}", id))
                    .andExpect(status().is(expectedStatusCode));

            MockHttpServletResponse response = result.andReturn().getResponse();

            if(expectedStatusCode == 200) {
                return response.getContentAsString();
            }

            return mapper.readValue(response.getContentAsString(), ErrorResponse.class);
        } catch (Exception e) {
            fail(String.format("deleteExercise failed, error: %s", e.getMessage()));
        }

        throw new RuntimeException("deleteExercise() failed.");
    }

}
