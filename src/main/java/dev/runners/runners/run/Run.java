package dev.runners.runners.run;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

import jakarta.validation.constraints.*;


public class Run {

    @Id
    Integer id;

    @NotEmpty
    String title;

    LocalDateTime startedOn;

    LocalDateTime completedOn;

    @Positive
    Integer miles;

    Location location;

    @Version
    Integer version;

    public Run() {}

    public Run(Integer id, String title, LocalDateTime startedOn, LocalDateTime completedOn, Integer miles,
        Location location) {
        this.id = id;
        this.title = title;
        this.startedOn = startedOn;
        this.completedOn = completedOn;
        this.miles = miles;
        this.location = location;

        if (!completedOn.isAfter(startedOn)) {
            throw new IllegalArgumentException("Completed date must be after start date");
        }
        // if (title.isEmpty()) {
        // throw new IllegalArgumentException("Title cannot be empty");
        // }
        // if (miles < 1 || !(miles instanceof Integer)) {
        // throw new IllegalArgumentException("Miles must be a positive value");
        // }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getStartedOn() {
        return startedOn;
    }

    public void setStartedOn(LocalDateTime startedOn) {
        this.startedOn = startedOn;
    }

    public LocalDateTime getCompletedOn() {
        return completedOn;
    }

    public void setCompletedOn(LocalDateTime completedOn) {
        this.completedOn = completedOn;
    }

    public Integer getMiles() {
        return miles;
    }

    public void setMiles(Integer miles) {
        this.miles = miles;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
