package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Fixture Test Suite")
class FixtureTest {

  @Test
  @DisplayName("Test Fixture Creation using three-argument constructor and getters")
  void testFixtureCreationAndGetters() {
    Fixture fixture = new Fixture("Computer", 1000, "A computer with a password screen active.");
    assertEquals("Computer", fixture.getName());
    assertEquals(1000, fixture.getWeight());
    assertEquals("A computer with a password screen active.", fixture.getDescription());
    // For three-argument constructor, optional fields should be null.
    assertNull(fixture.getPuzzle());
    assertNull(fixture.getStates());
    assertNull(fixture.getPicture());
  }

  @Test
  @DisplayName("Test Fixture Creation using full constructor and getters")
  void testFullConstructor() {
    Fixture fixture = new Fixture("Lamp", 150, "light puzzle", "on/off", "A desk lamp", "lamp.jpg");
    assertEquals("Lamp", fixture.getName());
    assertEquals(150, fixture.getWeight());
    assertEquals("A desk lamp", fixture.getDescription());
    assertEquals("light puzzle", fixture.getPuzzle());
    assertEquals("on/off", fixture.getStates());
    assertEquals("lamp.jpg", fixture.getPicture());
  }

  @Test
  @DisplayName("Test Default Constructor")
  void testDefaultConstructor() {
    Fixture fixture = new Fixture();
    // Default values: Strings should be null and weight should be 0.
    assertNull(fixture.getName());
    assertEquals(0, fixture.getWeight());
    assertNull(fixture.getPuzzle());
    assertNull(fixture.getStates());
    assertNull(fixture.getDescription());
    assertNull(fixture.getPicture());
  }

  @Test
  @DisplayName("Test Equals and HashCode Methods ignoring non-essential fields")
  void testEqualsAndHashCode() {
    // Although puzzle, states, and picture differ, equality is based on name, weight, and description.
    Fixture fixture1 = new Fixture("Desk", 250, "puzzle1", "state1", "A wooden desk.", "desk.jpg");
    Fixture fixture2 = new Fixture("Desk", 250, "puzzle2", "state2", "A wooden desk.", "anotherDesk.jpg");
    assertEquals(fixture1, fixture2);
    assertEquals(fixture1.hashCode(), fixture2.hashCode());
  }

  @Test
  @DisplayName("Test ToString Method")
  void testToString() {
    Fixture fixture = new Fixture("Bookshelf", 300, "A large bookshelf.");
    String expected = "Fixture [name=Bookshelf, weight=300, description=A large bookshelf.]";
    assertEquals(expected, fixture.toString());
  }
}
