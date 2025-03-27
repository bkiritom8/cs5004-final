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
    // For the three-argument constructor, optional fields should be null.
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
    // Create two fixtures with identical key properties but different optional fields.
    Fixture fixture1 = new Fixture("Desk", 250, "puzzle1", "state1", "A wooden desk.", "desk.jpg");
    Fixture fixture2 = new Fixture("Desk", 250, "puzzle2", "state2", "A wooden desk.", "anotherDesk.jpg");
    // Create a fixture with a different description.
    Fixture fixture3 = new Fixture("Desk", 250, "puzzle1", "state1", "A wooden desk with drawers.", "desk.jpg");

    // Fixtures with the same name, weight, and description should be equal.
    assertEquals(fixture1, fixture2, "Fixtures with matching key fields should be equal");
    assertEquals(fixture1.hashCode(), fixture2.hashCode(), "Hash codes should match for equal fixtures");
    // Fixtures with different descriptions should not be equal.
    assertNotEquals(fixture1, fixture3, "Fixtures with differing descriptions should not be equal");
  }

  @Test
  @DisplayName("Test Equals with Self, Null, and Different Type")
  void testEqualsSpecialCases() {
    Fixture fixture = new Fixture("Bookshelf", 300, "A large bookshelf.");
    // A fixture should be equal to itself.
    assertTrue(fixture.equals(fixture), "Fixture should be equal to itself");
    // A fixture should not be equal to null.
    assertFalse(fixture.equals(null), "Fixture should not be equal to null");
    // A fixture should not be equal to an object of a different type.
    assertFalse(fixture.equals("Not a fixture"), "Fixture should not be equal to an object of a different type");
  }

  @Test
  @DisplayName("Test HashCode Consistency")
  void testHashCodeConsistency() {
    Fixture fixture = new Fixture("Couch", 500, "A comfy couch.");
    int hash1 = fixture.hashCode();
    int hash2 = fixture.hashCode();
    assertEquals(hash1, hash2, "Hash code should be consistent across multiple invocations");
  }

  @Test
  @DisplayName("Test ToString Method")
  void testToString() {
    Fixture fixture = new Fixture("Bookshelf", 300, "A large bookshelf.");
    String expected = "Fixture [name=Bookshelf, weight=300, description=A large bookshelf.]";
    assertEquals(expected, fixture.toString(), "toString should return the expected format");
  }
}
