package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Fixture Test Suite")
class FixtureTest {

  @Test
  @DisplayName("Test Fixture Creation and Getters")
  void testFixtureCreationAndGetters() {
    Fixture fixture = new Fixture("Computer", 1000, "A computer with a password screen active.");
    assertEquals("Computer", fixture.getName());
    assertEquals(1000, fixture.getWeight());
    assertEquals("A computer with a password screen active.", fixture.getDescription());
    assertNull(fixture.getPuzzle());
    assertNull(fixture.getStates());
    assertNull(fixture.getPicture());
  }

  @Test
  @DisplayName("Test Equals and HashCode Methods")
  void testEqualsAndHashCode() {
    Fixture fixture1 = new Fixture("Desk", 250, "A wooden desk.");
    Fixture fixture2 = new Fixture("Desk", 250, "A wooden desk.");
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
