package model;

import org.testng.annotations.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit tests for the Fixture class.
 */
public class FixtureTest {

  @Test
  public void testFixtureCreation() {
    Fixture fixture = new Fixture("Computer", 1000, "A computer with a password screen active.");
    assertEquals("Computer", fixture.getName());
    assertEquals(1000, fixture.getWeight());
    assertEquals("A computer with a password screen active.", fixture.getDescription());
    assertNull(fixture.getPicture());
  }

  @Test
  public void testEqualsAndHashCode() {
    Fixture fixture1 = new Fixture("Desk", 250, "A wooden desk.");
    Fixture fixture2 = new Fixture("Desk", 250, "A wooden desk.");
    assertEquals(fixture1, fixture2);
    assertEquals(fixture1.hashCode(), fixture2.hashCode());
  }

  @Test
  public void testToString() {
    Fixture fixture = new Fixture("Bookshelf", 300, "A large bookshelf.");
    String expected = "Fixture [name=Bookshelf, weight=300, description=A large bookshelf.]";
    assertEquals(expected, fixture.toString());
  }
}