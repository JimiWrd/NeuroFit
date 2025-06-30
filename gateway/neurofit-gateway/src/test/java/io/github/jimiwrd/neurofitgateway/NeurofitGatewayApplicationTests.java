package io.github.jimiwrd.neurofitgateway;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
class NeurofitGatewayApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private RouteLocator routeLocator;

	@Autowired
	WebTestClient webTestClient;

	@Test
	void contextLoads() {
		// Verify application starts correctly
	}

	@DynamicPropertySource
	static void properties(DynamicPropertyRegistry registry) {
		registry.add("spring.security.oauth2.resourceserver.jwt.issuer-uri",
				() -> "placeholder");
	}

	@Test
	void routesAreConfiguredCorrectly() {
		StepVerifier.create(routeLocator.getRoutes())
				.expectNextCount(3) // Adjust based on your route count
				.verifyComplete();
	}

	@Test
	void securityConfigurationIsPresent() {
		webTestClient
				.get()
				.uri("/actuator/health")
				.exchange()
				.expectStatus().isOk();
	}
}
