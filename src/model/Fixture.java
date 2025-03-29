package model;

import java.util.Objects;

/**
 * Represents a fixture element in the game (e.g., a computer, desk, painting, etc.).
 * Fields are designed to match the JSON structure. Note that some fields (such as puzzle or states)
 * may be null.
 */
public class Fixture {
  private String name;
  private int weight;       // Stored as an integer (JSON weight is provided as a string)
  private String puzzle;    // Optional: puzzle associated with the fixture
  private String states;    // Optional: state model (not used in HW8)
  private String description;
  private String picture;   // Optional: path or URL to a picture

  // Default constructor required for JSON deserialization
  public Fixture() {}

  public Fixture(String name, int weight, String puzzle, String states, String description, String picture) {
    this.name = name;
    this.weight = weight;
    this.puzzle = puzzle;
    this.states = states;
    this.description = description;
    this.picture = picture;
  }

  /**
   * Overloaded constructor for convenience.
   * Initializes puzzle, states, and picture to null.
   */
  public Fixture(String name, int weight, String description) {
    this(name, weight, null, null, description, null);
  }

  // Getters
  public String getName() {
    return name;
  }

  public int getWeight() {
    return weight;
  }

  public String getPuzzle() {
    return puzzle;
  }

  public String getStates() {
    return states;
  }

  public String getDescription() {
    return description;
  }

  public String getPicture() {
    return picture;
  }

  @Override
  public String toString() {
    return "Fixture [name="
            + name
            + ", weight="
            + weight
            + ", description="
            + description
            + "]";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Fixture)) return false;
    Fixture fixture = (Fixture) o;
    return weight == fixture.weight
            && Objects.equals(name, fixture.name)
            && Objects.equals(description, fixture.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, weight, description);
  }
}