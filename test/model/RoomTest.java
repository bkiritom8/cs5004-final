package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Room Test Suite")
class RoomTest {

  @Test
  @DisplayName("Test Room Creation and Getters")
  void testRoomCreationAndGetters() {
    Room room = new Room("Hallway 1", 1, "A long hallway.", 2, 0, 0, 0);
    assertEquals("Hallway 1", room.getRoomName());
    assertEquals(1, room.getRoomNumber());
    assertEquals("A long hallway.", room.getDescription());
    assertEquals(2, room.getNorth());
    assertEquals(0, room.getSouth());
    assertEquals(0, room.getEast());
    assertEquals(0, room.getWest());
    // Check that the runtime fixture list is initially empty.
    assertTrue(room.getFixtureList().isEmpty());
  }

  @Test
  @DisplayName("Test Adding and Removing Fixtures")
  void testAddAndRemoveFixture() {
    Room room = new Room("Hallway 2", 2, "Another hallway.", 3, 1, 0, 0);
    Fixture fixture = new Fixture("Computer", 1000, "A computer with a password screen active.");
    room.addFixture(fixture);
    assertFalse(room.getFixtureList().isEmpty());
    assertTrue(room.getFixtureList().contains(fixture));

    boolean removed = room.removeFixture(fixture);
    assertTrue(removed);
    assertFalse(room.getFixtureList().contains(fixture));
  }

  @Test
  @DisplayName("Test Multiple Fixtures in a Room")
  void testMultipleFixtures() {
    Room room = new Room("Exhibit", 3, "A museum exhibit.", 0, 0, 0, 0);
    Fixture fixture1 = new Fixture("Desk", 250, "A desk.");
    Fixture fixture2 = new Fixture("Bookshelf", 300, "A bookshelf.");
    room.addFixture(fixture1);
    room.addFixture(fixture2);
    assertEquals(2, room.getFixtureList().size());
  }
}
