package dev.runners.runners.run;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import jakarta.annotation.PostConstruct;

public class InMemoryRunRepository implements RunRepository {

  private List<Run> runs = new ArrayList<>();

  public List<Run> findAll() {
    return runs;
  }

  public Optional<Run> findById(Integer id) {
    return Optional.ofNullable(runs.stream().filter(run -> run.getId() == id).findFirst()
        .orElseThrow(RunNotFoundException::new));
  }

  public void create(Run run) {
    runs.add(run);
  }

  public void update(Run run, Integer id) {
    Optional<Run> runToUpdate = findById(id);

    if (runToUpdate.isPresent()) {
      runs.set(runs.indexOf(runToUpdate.get()), run);
    }
  }

  public void delete(Integer id) {
    runs.removeIf(run -> run.getId().equals(id));
  }

  @PostConstruct
  private void init() {
    runs.add(new Run(1, "Monday Morning Run", LocalDateTime.now(),
        LocalDateTime.now().plus(30, ChronoUnit.HOURS), 3,
        Location.INDOOR));
    runs.add(new Run(2, "Wednesday Evening Run", LocalDateTime.now(),
        LocalDateTime.now().plus(60, ChronoUnit.HOURS), 6,
        Location.INDOOR));
  }

  public int count() {
    return runs.size();
  }

  public void saveAll(List<Run> runs) {
    runs.stream().forEach(run -> create(run));
  }

  @SuppressWarnings("unlikely-arg-type")
  public List<Run> findByLocation(String location) {
    return runs.stream()
        .filter(run -> Objects.equals(run.getLocation(), location))
        .toList();
  }
}
