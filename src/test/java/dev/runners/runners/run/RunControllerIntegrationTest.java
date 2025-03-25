package dev.runners.runners.run;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RunControllerIntegrationTest {

  @LocalServerPort
  int randomServerPort;

  RestClient restClient;

  @BeforeEach
  void setUp() {
    restClient = RestClient.create("http://localhost:" + randomServerPort);
  }

  @SuppressWarnings("null")
  @Test
  void shouldFindAllRuns() {
    List<Run> runs = restClient.get().uri("/api/runs").retrieve().body(new ParameterizedTypeReference<>() {});

    assertEquals(10, runs.size());
  }
  
}
