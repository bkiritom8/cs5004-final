package model;

import java.util.Objects;

/**
 * Represents a fixture element in the game (e.g., a computer, desk, painting, etc.).
 * Fields match the JSON structure. Some fields like puzzle or states may be null.
 */
public class Fixture {
  private String name;
  private int weight;       // Stored as an integer (JSON weight is a string)
  private String puzzle;    // Optional: puzzle associated with the fixture
  private String states;    // Optional: state model (not used in HW8)
  private String description;
  private String picture;   // Optional: path or URL to a picture

  /**
   * Default constructor required for JSON deserialization.
   */
  public Fixture() {}

  /**
   * Constructs a Fixture with all possible fields.
   *
   * @param name the name of the fixture
   * @param weight the weight of the fixture
   * @param puzzle associated puzzle (nullable)
   * @param states state model (nullable)
   * @param description description of the fixture
   * @param picture path or URL to a picture (nullable)
   */
  public Fixture(String name, int weight, String puzzle, String states,
                 String description, String picture) {
    this.name = name;
    this.weight = weight;
    this.puzzle = puzzle;
    this.states = states;
    this.description = description;
    this.picture = picture;
  }

  /**
   * Convenience constructor initializing only name, weight, and description.
   * Puzzle, states, and picture are set to null.
   *
   * @param name the name of the fixture
   * @param weight the weight of the fixture
   * @param description description of the fixture
   */
  public Fixture(String name, int weight, String description) {
    this(name, weight, null, null, description, null);
  }

  /**
   * Returns the name of the fixture.
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the weight of the fixture.
   */
  public int getWeight() {
    return weight;
  }

  /**
   * Returns the name of the puzzle associated with this fixture, if any.
   */
  public String getPuzzle() {
    return puzzle;
  }

  /**
   * Returns the states string, if defined.
   */
  public String getStates() {
    return states;
  }

  /**
   * Returns the description of the fixture.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Returns the path or URL of the fixture's picture, if any.
   */
  public String getPicture() {
    return picture;
  }

  @Override
  public String toString() {
    return "Fixture [name=" + name + ", weight=" + weight
            + ", description=" + description + "]";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Fixture)) return false;
    Fixture fixture = (Fixture) o;
    return weight == fixture.weight &&
            Objects.equals(name, fixture.name) &&
            Objects.equals(description, fixture.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, weight, description);
  }

  /**
   * Defines an interaction between the fixture and a player.
   * By default, this returns false. Can be overridden in subclasses.
   *
   * @param player the player interacting with the fixture
   * @return true if interaction succeeds; false otherwise
   */
  public boolean interact(Player player) {
    return false;
  }
}