package io.github.jimiwrd.userservice.mock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dasniko.testcontainers.keycloak.KeycloakContainer;
import io.github.jimiwrd.userservice.UserServiceApplication;
import io.github.jimiwrd.userservice.error.ErrorResponse;
import io.github.jimiwrd.userservice.user.response.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.reactive.function.client.WebClient;
import org.testcontainers.containers.PostgreSQLContainer;

import java.time.Instant;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = UserServiceApplication.class)
@ContextConfiguration
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BaseMockTest {

    static PostgreSQLContainer<?> POSTGRES_CONTAINER = new PostgreSQLContainer<>("postgres:latest");

    static KeycloakContainer KEYCLOAK_CONTAINER = new KeycloakContainer();

    static {
        POSTGRES_CONTAINER.start();
        KEYCLOAK_CONTAINER.withRealmImportFile("/keycloak/neurofit-test-realm.json");
        KEYCLOAK_CONTAINER.start();
    }

    @Autowired
    protected MockMvc mvc;

    @Autowired
    protected ObjectMapper mapper;

    protected WebClient webClient;

    private String realm = "neurofit-test";
    private String keycloakBaseUrl = KEYCLOAK_CONTAINER.getAuthServerUrl();

    @Autowired
    private List<JpaRepository<?, ?>> repositories;

//    private List<String> usernames = new ArrayList<>();

    @DynamicPropertySource
    static void containerProperties(DynamicPropertyRegistry registry) {
        // POSTGRES config setup
        registry.add("spring.datasource.url", () -> POSTGRES_CONTAINER.getJdbcUrl() + "/organisation-service");
        registry.add("spring.datasource.password", () -> POSTGRES_CONTAINER.getPassword());
        registry.add("spring.datasource.username", () -> POSTGRES_CONTAINER.getUsername());

        // KEYCLOAK config setup
        registry.add("spring.security.oauth2.resourceserver.jwt.issuer-uri", () -> KEYCLOAK_CONTAINER.getAuthServerUrl() + "/realms/neurofit-test");
    }

    @BeforeEach
    void setup() {
        this.webClient = WebClient.create();
        repositories.forEach(JpaRepository::deleteAll);
    }

    public String createKeycloakUser(String username, String password, HttpStatus expectedStatus) throws Exception {
        deleteKeycloakUser(username);
        String adminUsername = "admin";
        String adminPassword = "admin";
        String clientId = "admin-cli";

        // 1. Get admin access token
        String tokenUrl = keycloakBaseUrl + "/realms/master/protocol/openid-connect/token";
        String body = "grant_type=password&client_id=" + clientId +
                "&username=" + adminUsername +
                "&password=" + adminPassword;

        String tokenResponse = webClient.post()
                .uri(tokenUrl)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        String accessToken = new ObjectMapper().readTree(tokenResponse).get("access_token").asText();

        assertThat(accessToken).isNotNull();

        // 2. Create user
        String createUserUrl = keycloakBaseUrl + "/admin/realms/" + realm + "/users";

        String userJson = "{ \"username\": \"" + username + "\", " +
                "\"email\": \"" + username + "@example.com\", " +
                "\"firstName\": \"Test\", " +
                "\"lastName\": \"User\", " +
                "\"enabled\": true, " +
                "\"credentials\": [{ \"type\": \"password\", \"value\": \"" + password + "\" }], " +
                "\"requiredActions\": [] }";

        HttpStatusCode status =  webClient.post()
                .uri(createUserUrl)
                .headers(headers -> {
                    headers.setBearerAuth(accessToken);
                    headers.setContentType(MediaType.APPLICATION_JSON);
                })
                .bodyValue(userJson)
                .retrieve()
                .toBodilessEntity()
                .block()
                .getStatusCode();

        assertThat(status).isEqualTo(expectedStatus);

        return accessToken;
    }

    // TODO - Create assignRoleToUser method

    public Jwt loginKeycloakUser(String username, String password) throws JsonProcessingException {
        String loginUrl = String.format("%s/realms/%s/protocol/openid-connect/token", keycloakBaseUrl, realm);
        String clientId = "user-service";

        String body = "grant_type=password&client_id=" + clientId +
                "&username=" + username +
                "&password=" + password;

        String response = webClient.post()
                .uri(loginUrl)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        String accessToken = new ObjectMapper().readTree(response).get("access_token").asText();

        // Decode JWT header and claims without verifying signature
        String[] parts = accessToken.split("\\.");
        String claimsJson = new String(Base64.getUrlDecoder().decode(parts[1]));
        Map claims = new ObjectMapper().readValue(claimsJson, Map.class);

        Map<String, Object> fixedClaims = new HashMap<>(claims);
        for (String key : List.of("exp", "iat")) {
            Object value = fixedClaims.get(key);
            if (value instanceof Integer) {
                fixedClaims.put(key, Instant.ofEpochSecond((Integer) value));
            } else if (value instanceof Long) {
                fixedClaims.put(key, Instant.ofEpochSecond((Long) value));
            }
        }

        return Jwt.withTokenValue(accessToken)
                .headers(h -> h.put("alg", "RS256"))
                .claims(c -> c.putAll(fixedClaims))
                .build();
    }


    public Object findOrCreateUser(String jwt, HttpStatus expectedStatus) {
        try {
            ResultActions result = mvc.perform(MockMvcRequestBuilders
                    .post("/users").with(csrf())
                    .header("Authorization", "Bearer " + jwt)
                    .contentType(MediaType.APPLICATION_JSON));

            MockHttpServletResponse response = result.andReturn().getResponse();

            System.out.println("XXX: " + response.getContentAsString() +" YYYY");

            if(expectedStatus == HttpStatus.UNAUTHORIZED) {
                result.andExpect(status().is4xxClientError());
                return mapper.readValue(response.getContentAsString(), ErrorResponse.class);
            }

            result.andExpect(status().isOk());
            return mapper.readValue(response.getContentAsString(), UserResponse.class);

        } catch (Exception e) {
            fail(String.format("Create user failed, error: %s", e.getMessage()));
        }

        throw new RuntimeException("createUser() failed");
    }

    public void deleteKeycloakUser(String username) throws Exception {
        String adminUsername = "admin";
        String adminPassword = "admin";
        String clientId = "admin-cli";

        // 1. Get admin access token
        String tokenUrl = keycloakBaseUrl + "/realms/master/protocol/openid-connect/token";
        String body = "grant_type=password&client_id=" + clientId +
                "&username=" + adminUsername +
                "&password=" + adminPassword;

        String tokenResponse = webClient.post()
                .uri(tokenUrl)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        String accessToken = new ObjectMapper().readTree(tokenResponse).get("access_token").asText();

        // 2. Find user by username
        String getUsersUrl = keycloakBaseUrl + "/admin/realms/" + realm + "/users?username=" + username;
        String usersResponse = webClient.get()
                .uri(getUsersUrl)
                .headers(headers -> headers.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        JsonNode users = new ObjectMapper().readTree(usersResponse);
        if (users.isArray() && !users.isEmpty()) {
            String userId = users.get(0).get("id").asText();

            // 3. Delete user
            String deleteUserUrl = keycloakBaseUrl + "/admin/realms/" + realm + "/users/" + userId;
            webClient.delete()
                    .uri(deleteUserUrl)
                    .headers(headers -> headers.setBearerAuth(accessToken))
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        }
    }

}
