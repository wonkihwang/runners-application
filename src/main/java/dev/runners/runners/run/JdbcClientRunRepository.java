package dev.runners.runners.run;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class JdbcClientRunRepository implements RunRepository {

  private final JdbcClient jdbcClient;

  public JdbcClientRunRepository(JdbcClient jdbcClient) {
    this.jdbcClient = jdbcClient;
  }

  public List<Run> findAll() {
    return jdbcClient.sql("SELECT * FROM run")
        .query(getRunRowMapper())
        .list();
  }

  public Optional<Run> findById(Integer id) {
    return jdbcClient.sql("SELECT * FROM run WHERE id = :id")
        .param("id", id)
        .query(Run.class)
        .optional();
  }

  public void create(Run run) {
    var updated = jdbcClient
        .sql("INSERT INTO Run(id, title, started_on, completed_on, miles, location) values(?, ?, ?, ?, ?, ?)")
        .params(
            List.of(run.getId(), run.getTitle(), run.getStartedOn().format(formatter), run.getCompletedOn().format(formatter), run.getMiles(),
                run.getLocation().toString()))
        .update();

    Assert.state(updated == 1, "Failed to create run " + run.getTitle());
  }

  public void update(Run run, Integer id) {
    var updated = jdbcClient
        .sql("update run set title = ?, started_on = ?, completed_on = ?, miles = ?, location = ? where id = ?")
        .params(List.of(run.getTitle(), run.getStartedOn().format(formatter), run.getCompletedOn().format(formatter), run.getMiles(),
            run.getLocation().toString(), id))
        .update();

    Assert.state(updated == 1, "Failed to update run " + id);
  }

  public void delete(Integer id) {
    var updated = jdbcClient.sql("DELETE FROM run WHERE id = :id")
        .param("id", id)
        .update();

    Assert.state(updated == 1, "Failed to delete run " + id);
  }

  public int count() {
      return jdbcClient.sql("select * from run").query().listOfRows().size();
  }

  public void saveAll(List<Run> runs) {
      runs.stream().forEach(this::create);
  }

  public List<Run> findByLocation(String location) {
      return jdbcClient.sql("select * from run where location = :location")
              .param("location", location)
              .query(Run.class)
              .list();
  }

  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  private RowMapper<Run> getRunRowMapper() {
    return new RowMapper<Run>() {
      @Override
      public Run mapRow(@SuppressWarnings("null") ResultSet rs, int rowNum) throws SQLException {
        Run run = new Run(rs.getInt("id"), rs.getString("title"),
            LocalDateTime.parse(rs.getString("started_on"), formatter),
            LocalDateTime.parse(rs.getString("completed_on"), formatter), rs.getInt("miles"),
            Location.valueOf(rs.getString("location")));
        return run;
      }
    };
  }
}
