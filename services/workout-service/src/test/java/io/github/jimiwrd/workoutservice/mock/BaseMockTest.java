package io.github.jimiwrd.workoutservice.mock;

import io.github.jimiwrd.workoutservice.WorkoutServiceApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;

import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = WorkoutServiceApplication.class)
@ContextConfiguration
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BaseMockTest {

    static MongoDBContainer MONGO_CONTAINER = new MongoDBContainer("mongo:latest");

    private List<MongoRepository<?, ?>> repositories;

    static {
        MONGO_CONTAINER.start();
    }

    @Autowired
    protected MockMvc mvc;

//    @DynamicPropertySource
//    static void containerProperties(DynamicPropertyRegistry registry) {
//        registry.add();
//    }

}
