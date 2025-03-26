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
  @DisplayName("Test getRoomName")
  void getRoomName() {
    Room room = new Room("Hallway", 1, "A long hallway.", 2, 0, 0, 0);
    assertEquals("Hallway", room.getRoomName());
  }

  @Test
  @DisplayName("Test getRoomNumber")
  void getRoomNumber() {
    Room room = new Room("Hallway", 1, "A long hallway.", 2, 0, 0, 0);
    assertEquals(1, room.getRoomNumber());
  }

  @Test
  @DisplayName("Test getDescription")
  void getDescription() {
    Room room = new Room("Hallway", 1, "A long hallway.", 2, 0, 0, 0);
    assertEquals("A long hallway.", room.getDescription());
  }

  @Test
  @DisplayName("Test getNorth")
  void getNorth() {
    Room room = new Room("Hallway", 1, "A long hallway.", 2, 0, 0, 0);
    assertEquals(2, room.getNorth());
  }

  @Test
  @DisplayName("Test getSouth")
  void getSouth() {
    Room room = new Room("Hallway", 1, "A long hallway.", 2, 0, 0, 0);
    assertEquals(0, room.getSouth());
  }

  @Test
  @DisplayName("Test getEast")
  void getEast() {
    Room room = new Room("Hallway", 1, "A long hallway.", 2, 0, 0, 0);
    assertEquals(0, room.getEast());
  }

  @Test
  @DisplayName("Test getWest")
  void getWest() {
    Room room = new Room("Hallway", 1, "A long hallway.", 2, 0, 0, 0);
    assertEquals(0, room.getWest());
  }

  @Test
  @DisplayName("Test getPuzzle")
  void getPuzzle() {
    Room room = new Room("Room with Puzzle", 5, "A room with a locked door.", 0, 0, 0, 0);
    Puzzle puzzle = new Puzzle("Lock", true, true, false, "key", 25, "A tricky lock.", "", "");
    room.setPuzzle(puzzle);
    assertEquals(puzzle, room.getPuzzle());
  }

  @Test
  @DisplayName("Test getMonster")
  void getMonster() {
    Room room = new Room("Dungeon", 10, "A dark dungeon.", 0, 0, 0, 0);
    Monster monster = new Monster("Goblin", true, 15, true, "Slash!", "Tiny and aggressive", "", 50, "Sword", "");
    room.setMonster(monster);
    assertEquals(monster, room.getMonster());
  }

  @Test
  @DisplayName("Test getItemsField")
  void getItemsField() {
    Room room = new Room("Storage", 6, "A dusty storage room.", 0, 0, 0, 0, "", "", "Lantern, Rope", "", "");
    assertEquals("Lantern, Rope", room.getItemsField());
  }

  @Test
  @DisplayName("Test getFixtureNames")
  void getFixtureNames() {
    Room room = new Room("Library", 4, "A library with books.", 0, 0, 0, 0, "", "", "", "Desk, Bookshelf", "");
    assertEquals("Desk, Bookshelf", room.getFixtureNames());
  }

  @Test
  @DisplayName("Test getPicture")
  void getPicture() {
    Room room = new Room("Gallery", 9, "An art gallery.", 0, 0, 0, 0, "", "", "", "", "gallery_image.png");
    assertEquals("gallery_image.png", room.getPicture());
  }

  @Test
  @DisplayName("Test getFixtureList")
  void getFixtureList() {
    Room room = new Room("Exhibit", 3, "A museum exhibit.", 0, 0, 0, 0);
    Fixture fixture1 = new Fixture("Desk", 250, "A desk.");
    room.addFixture(fixture1);
    assertEquals(1, room.getFixtureList().size());
  }

  @Test
  @DisplayName("Test addFixture")
  void addFixture() {
    Room room = new Room("Exhibit", 3, "A museum exhibit.", 0, 0, 0, 0);
    Fixture fixture = new Fixture("Desk", 250, "A desk.");
    room.addFixture(fixture);
    assertTrue(room.getFixtureList().contains(fixture));
  }

  @Test
  @DisplayName("Test removeFixture")
  void removeFixture() {
    Room room = new Room("Exhibit", 3, "A museum exhibit.", 0, 0, 0, 0);
    Fixture fixture = new Fixture("Desk", 250, "A desk.");
    room.addFixture(fixture);
    room.removeFixture(fixture);
    assertFalse(room.getFixtureList().contains(fixture));
  }

  @Test
  @DisplayName("Test addItem")
  void addItem() {
    Room room = new Room("Storage", 5, "A dusty storage room.", 0, 0, 0, 0);
    Item item = new Item("Lantern", 2, 3, 3, 10, "Shines bright", "An old lantern.");
    room.addItem(item);
    assertTrue(room.getItems().contains(item));
  }

  @Test
  @DisplayName("Test removeItem")
  void removeItem() {
    Room room = new Room("Storage", 5, "A dusty storage room.", 0, 0, 0, 0);
    Item item = new Item("Lantern", 2, 3, 3, 10, "Shines bright", "An old lantern.");
    room.addItem(item);
    room.removeItem(item);
    assertFalse(room.getItems().contains(item));
  }

  @Test
  @DisplayName("Test getItem")
  void getItem() {
    Room room = new Room("Storage", 5, "A dusty storage room.", 0, 0, 0, 0);
    Item item = new Item("Lantern", 2, 3, 3, 10, "Shines bright", "An old lantern.");
    room.addItem(item);
    assertEquals(item, room.getItem("Lantern"));
  }

  @Test
  @DisplayName("Test clearItems")
  void clearItems() {
    Room room = new Room("Storage", 5, "A dusty storage room.", 0, 0, 0, 0);
    Item item1 = new Item("Lantern", 2, 3, 3, 10, "Shines bright", "An old lantern.");
    Item item2 = new Item("Rope", 1, 1, 1, 5, "Useful", "A long rope.");
    room.addItem(item1);
    room.addItem(item2);
    room.clearItems();
    assertTrue(room.getItems().isEmpty());
  }

  @Test
  @DisplayName("Test getItems")
  void getItems() {
    Room room = new Room("Storage", 5, "A dusty storage room.", 0, 0, 0, 0);
    Item item = new Item("Lantern", 2, 3, 3, 10, "Shines bright", "An old lantern.");
    room.addItem(item);
    assertEquals(1, room.getItems().size());
  }

  @Test
  @DisplayName("Test setPuzzle")
  void setPuzzle() {
    Room room = new Room("Puzzle Room", 8, "A sealed chamber", 0, 0, 0, 0);
    Puzzle puzzle = new Puzzle("Lock", true, true, false, "key", 25, "A tricky lock.", "", "");
    room.setPuzzle(puzzle);
    assertEquals(puzzle, room.getPuzzle());
  }

  @Test
  @DisplayName("Test setMonster")
  void setMonster() {
    Room room = new Room("Dungeon", 10, "A dark dungeon.", 0, 0, 0, 0);
    Monster monster = new Monster("Goblin", true, 15, true, "Slash!", "Tiny and aggressive", "", 50, "Sword", "");
    room.setMonster(monster);
    assertEquals(monster, room.getMonster());
  }

  @Test
  @DisplayName("Test setExit")
  void setExit() {
    Room room1 = new Room("One", 1, "Room One", 0, 0, 0, 0);
    Room room2 = new Room("Two", 2, "Room Two", 0, 0, 0, 0);
    room1.setExit(Direction.EAST, room2);
    assertEquals(room2, room1.getExit(Direction.EAST));
  }

  @Test
  @DisplayName("Test getExit")
  void getExit() {
    Room room1 = new Room("One", 1, "Room One", 0, 0, 0, 0);
    Room room2 = new Room("Two", 2, "Room Two", 0, 0, 0, 0);
    room1.setExit(Direction.EAST, room2);
    assertEquals(room2, room1.getExit(Direction.EAST));
  }

  @Test
  @DisplayName("Test toString")
  void testToString() {
    Room room = new Room("Hallway", 1, "A long hallway.", 2, 0, 0, 0);
    assertNotNull(room.toString());
  }

  @Test
  @DisplayName("Test equals")
  void testEquals() {
    Room room1 = new Room("Hallway", 1, "A long hallway.", 2, 0, 0, 0);
    Room room2 = new Room("Hallway", 1, "A long hallway.", 2, 0, 0, 0);
    assertTrue(room1.equals(room2));
  }

  @Test
  @DisplayName("Test hashCode")
  void testHashCode() {
    Room room1 = new Room("Hallway", 1, "A long hallway.", 2, 0, 0, 0);
    Room room2 = new Room("Hallway", 1, "A long hallway.", 2, 0, 0, 0);
    assertEquals(room1.hashCode(), room2.hashCode());
  }
}
