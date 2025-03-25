package dev.runners.runners.run;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.runners.runners.RunnersApplication;

@Component
public class RunJsonDataLoader implements CommandLineRunner {

  private static final Logger logger = LoggerFactory.getLogger(RunnersApplication.class);
  private final JdbcClientRunRepository runRepository;
  private final ObjectMapper objectMapper;

  public RunJsonDataLoader(JdbcClientRunRepository runRepository, ObjectMapper objectMapper) {
    this.runRepository = runRepository;
    this.objectMapper = objectMapper;
  }

  @Override
  public void run(String... args) throws Exception {
    if (runRepository.count() == 0) {
      try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/runs.json")) {
        Runs allRuns = objectMapper.readValue(inputStream, Runs.class);
        logger.info("Reading {} runs from JSON data and saving it to the H2 in-memory database.", allRuns.getRuns().size());
        runRepository.saveAll(allRuns.getRuns());
      } catch (IOException e) {
        throw new RuntimeException("Failed to read JSON data", e);
      }
    } else {
      logger.info("Not loading Runs from JSON data because the collection contains data.");
    }
  }

}
