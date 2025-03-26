package model;

/**
 * Represents an immovable fixture in the game.
 * For example, a computer, painting, or desk found in a room.
 */
public class Fixture {
  private String name;
  private int weight;
  private String description;
  private String picture; // Optional picture file path or URL

  /**
   * Constructor for Fixture with picture.
   *
   * @param name        the fixture name
   * @param weight      the weight (should be high for immovable objects)
   * @param description the description shown to the player
   * @param picture     an optional picture associated with the fixture
   */
  public Fixture(String name, int weight, String description, String picture) {
    this.name = name;
    this.weight = weight;
    this.description = description;
    this.picture = picture;
  }

  /**
   * Constructor for Fixture without picture.
   *
   * @param name        the fixture name
   * @param weight      the weight of the fixture
   * @param description the description shown to the player
   */
  public Fixture(String name, int weight, String description) {
    this(name, weight, description, null);
  }

  public String getName() {

    return name;
  }

  public int getWeight() {

    return weight;
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
            + description + "]";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Fixture fixture = (Fixture) o;
    return weight == fixture.weight
            && name.equals(fixture.name)
            && description.equals(fixture.description);
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(name, weight, description);
  }
}

