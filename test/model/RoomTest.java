package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

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

  @Test
  @DisplayName("Test Adding and Removing Items")
  void testAddRemoveItems() {
    Room room = new Room("Storage", 5, "A dusty storage room.", 0, 0, 0, 0);
    Item item = new Item("Lantern", 2, 3, 3, 10, "Shines bright", "An old lantern.");

    room.addItem(item);
    assertEquals(1, room.getItems().size());
    assertEquals(item, room.getItem("Lantern"));

    room.removeItem(item);
    assertTrue(room.getItems().isEmpty());
  }

  @Test
  @DisplayName("Test Puzzle and Monster Assignment")
  void testPuzzleAndMonsterAssignment() {
    Room room = new Room("Chamber", 8, "A sealed chamber", 0, 0, 0, 0);
    Puzzle puzzle = new Puzzle("Lock", true, true, false, "key", 25, "A tricky lock.", "", "");
    Monster monster = new Monster("Goblin", true, 15, true, "Slash!", "Tiny and aggressive", "", 50, "Sword", "");

    room.setPuzzle(puzzle);
    room.setMonster(monster);

    assertEquals(puzzle, room.getPuzzle());
    assertEquals(monster, room.getMonster());
  }

  @Test
  @DisplayName("Test Room Exit Assignment with setExit and getExit")
  void testSetAndGetExit() {
    Room room1 = new Room("One", 1, "Room One", 0, 0, 0, 0);
    Room room2 = new Room("Two", 2, "Room Two", 0, 0, 0, 0);

    room1.setExit(Direction.EAST, room2);
    assertEquals(room2, room1.getExit(Direction.EAST));
  }

  @Test
  void getRoomName() {
  }

  @Test
  void getRoomNumber() {
  }

  @Test
  void getDescription() {
  }

  @Test
  void getNorth() {
  }

  @Test
  void getSouth() {
  }

  @Test
  void getEast() {
  }

  @Test
  void getWest() {
  }

  @Test
  void getPuzzle() {
  }

  @Test
  void getMonster() {
  }

  @Test
  void getItemsField() {
  }

  @Test
  void getFixtureNames() {
  }

  @Test
  void getPicture() {
  }

  @Test
  void getFixtureList() {
  }

  @Test
  void addFixture() {
  }

  @Test
  void removeFixture() {
  }

  @Test
  void addItem() {
  }

  @Test
  void removeItem() {
  }

  @Test
  void getItem() {
  }

  @Test
  void clearItems() {
  }

  @Test
  void getItems() {
  }

  @Test
  void setPuzzle() {
  }

  @Test
  void testGetPuzzle() {
  }

  @Test
  void setMonster() {
  }

  @Test
  void testGetMonster() {
  }

  @Test
  void setExit() {
  }

  @Test
  void getExit() {
  }

  @Test
  void testToString() {
  }

  @Test
  void testEquals() {
  }

  @Test
  void testHashCode() {
  }
}
