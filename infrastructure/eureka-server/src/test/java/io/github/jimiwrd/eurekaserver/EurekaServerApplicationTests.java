package io.github.jimiwrd.eurekaserver;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EurekaServerApplicationTests {

	@LocalServerPort
	private int port;

	@Test
	void contextLoads() {}

	@Test
	void eurekaServerIsUp() {
		String url = String.format("http://localhost:%d/eureka/apps", port);
		ResponseEntity<String> response = new RestTemplate()
				.getForEntity(url, String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
}
