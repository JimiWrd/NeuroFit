package io.github.jimiwrd.neurofitgateway;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {
        "spring.profiles.active=test"
    }
)
class NeurofitGatewayApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private RouteLocator routeLocator;

	@Autowired
	WebTestClient webTestClient;

	@Test
	void contextLoads() {}

	@Test
	void securityConfigurationIsPresent() {
		webTestClient
				.get()
				.uri("/actuator/health")
				.exchange()
				.expectStatus().isOk();
	}
}
