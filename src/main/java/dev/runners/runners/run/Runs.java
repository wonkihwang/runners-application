package dev.runners.runners.run;

import java.util.List;

public class Runs {
  private List<Run> runs;

  public Runs(List<Run> runs) {
    this.runs = runs;
  }

  public List<Run> getRuns() {
    return runs;
  }

  public void setRuns(List<Run> runs) {
    this.runs = runs;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    Runs runs1 = (Runs) o;

    return runs != null ? runs.equals(runs1.runs) : runs1.runs == null;
  }

  @Override
  public int hashCode() {
    return runs != null ? runs.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "Runs{" +
        "runs=" + runs +
        '}';
  }
}