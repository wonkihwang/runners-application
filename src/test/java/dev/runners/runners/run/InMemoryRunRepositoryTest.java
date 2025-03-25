package dev.runners.runners.run;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InMemoryRunRepositoryTest {

  InMemoryRunRepository repo;

  @BeforeEach
  public void setup() {
    repo = new InMemoryRunRepository();

    repo.create(new Run(1, "Monday Morning Run", LocalDateTime.now(),
        LocalDateTime.now().plus(30, ChronoUnit.HOURS), 3,
        Location.INDOOR));
    repo.create(new Run(2, "Wednesday Evening Run", LocalDateTime.now(),
        LocalDateTime.now().plus(60, ChronoUnit.HOURS), 6,
        Location.INDOOR));
  }

  @Test
  void shouldFindAllRuns() {
    List<Run> runs = repo.findAll();
    assertEquals(2, runs.size(), "Should find 2 runs");
  }

  @Test
  void shouldFindRunWithValidId() {
    var run = repo.findById(1).get();
    assertEquals("Monday Morning Run", run.getTitle());
    assertEquals(3, run.getMiles());
  }

  @Test
  void shouldNotFindRunWithInvalidId() {
    RunNotFoundException notFoundException = assertThrows(
        RunNotFoundException.class,
        () -> repo.findById(3).get());

    assertEquals("Run Not Found", notFoundException.getMessage());
  }

  @Test
  void shouldCreateNewRun() {
    repo.create(new Run(3,
        "Friday Morning Run",
        LocalDateTime.now(),
        LocalDateTime.now().plus(30, ChronoUnit.MINUTES),
        3,
        Location.INDOOR));
    List<Run> runs = repo.findAll();
    assertEquals(3, runs.size());
  }

  @Test
  void shouldUpdateRun() {
    repo.update(new Run(1,
        "Monday Morning Run",
        LocalDateTime.now(),
        LocalDateTime.now().plus(30, ChronoUnit.MINUTES),
        5,
        Location.OUTDOOR), 1);
    var run = repo.findById(1).get();
    assertEquals("Monday Morning Run", run.getTitle());
    assertEquals(5, run.getMiles());
    assertEquals(Location.OUTDOOR, run.getLocation());
  }

  @Test
  void shouldDeleteRun() {
    repo.delete(1);
    List<Run> runs = repo.findAll();
    assertEquals(1, runs.size());
  }

}
