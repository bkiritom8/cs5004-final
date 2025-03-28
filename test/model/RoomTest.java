package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

@DisplayName("Room Test Suite")
class RoomTest {

  // Helper method for creating a Room with basic exit info.
  private Room createRoom(String name, String roomNumber, String description,
                          String north, String south, String east, String west, String field1, String field2, String itemsField, String field3, String picture) {
    Map<Direction, String> exits = new HashMap<>();
    exits.put(Direction.NORTH, north);
    exits.put(Direction.SOUTH, south);
    exits.put(Direction.EAST, east);
    exits.put(Direction.WEST, west);
    return new Room(name, roomNumber, description, exits, field1, field2, itemsField, field3, picture);
  }

  // Helper for the extended Room constructor (if your Room class supports extra fields).
  private Room createRoomExtended(String name, String roomNumber, String description,
                                  String north, String south, String east, String west,
                                  String field1, String field2, String itemsField, String field3, String picture) {
    Map<Direction, String> exits = new HashMap<>();
    exits.put(Direction.NORTH, north);
    exits.put(Direction.SOUTH, south);
    exits.put(Direction.EAST, east);
    exits.put(Direction.WEST, west);
    return new Room(name, roomNumber, description, exits, field1, field2, itemsField, field3, picture);
  }

  @Test
  @DisplayName("Test Room Creation and Getters")
  void testRoomCreationAndGetters() {
    Room room = createRoom("Hallway 1", "1", "A long hallway.", "2", "0", "0", "0", field1, field2, itemsField, field3, picture);
    assertEquals("Hallway 1", room.getRoomName());
    assertEquals("1", room.getRoomNumber());
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
    Room room = createRoom("Hallway 2", "2", "Another hallway.", "3", "1", "0", "0", field1, field2, itemsField, field3, picture);
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
    Room room = createRoom("Exhibit", "3", "A museum exhibit.", "0", "0", "0", "0", field1, field2, itemsField, field3, picture);
    Fixture fixture1 = new Fixture("Desk", 250, "A desk.");
    Fixture fixture2 = new Fixture("Bookshelf", 300, "A bookshelf.");
    room.addFixture(fixture1);
    room.addFixture(fixture2);
    assertEquals(2, room.getFixtureList().size());
  }

  @Test
  @DisplayName("Test Adding and Removing Items")
  void testAddRemoveItems() {
    Room room = createRoom("Storage", "5", "A dusty storage room.", "0", "0", "0", "0", field1, field2, itemsField, field3, picture);
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
    Room room = createRoom("Chamber", "8", "A sealed chamber", "0", "0", "0", "0", field1, field2, itemsField, field3, picture);
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
    Room room1 = createRoom("One", "1", "Room One", "0", "0", "0", "0", field1, field2, itemsField, field3, picture);
    Room room2 = createRoom("Two", "2", "Room Two", "0", "0", "0", "0", field1, field2, itemsField, field3, picture);

    room1.setExit(Direction.EAST, room2);
    assertEquals(room2, room1.getExit(Direction.EAST));
  }

  // Additional tests for getters and extended fields

  @Test
  @DisplayName("Test getRoomName")
  void getRoomName() {
    Room room = createRoom("Hallway", "1", "A long hallway.", "2", "0", "0", "0", field1, field2, itemsField, field3, picture);
    assertEquals("Hallway", room.getRoomName());
  }

  @Test
  @DisplayName("Test getRoomNumber")
  void getRoomNumber() {
    Room room = createRoom("Hallway", "1", "A long hallway.", "2", "0", "0", "0", field1, field2, itemsField, field3, picture);
    assertEquals("1", room.getRoomNumber());
  }

  @Test
  @DisplayName("Test getDescription")
  void getDescription() {
    Room room = createRoom("Hallway", "1", "A long hallway.", "2", "0", "0", "0", field1, field2, itemsField, field3, picture);
    assertEquals("A long hallway.", room.getDescription());
  }

  @Test
  @DisplayName("Test getNorth")
  void getNorth() {
    Room room = createRoom("Hallway", "1", "A long hallway.", "2", "0", "0", "0", field1, field2, itemsField, field3, picture);
    assertEquals(2, room.getNorth());
  }

  @Test
  @DisplayName("Test getSouth")
  void getSouth() {
    Room room = createRoom("Hallway", "1", "A long hallway.", "2", "0", "0", "0", field1, field2, itemsField, field3, picture);
    assertEquals(0, room.getSouth());
  }

  @Test
  @DisplayName("Test getEast")
  void getEast() {
    Room room = createRoom("Hallway", "1", "A long hallway.", "2", "0", "0", "0", field1, field2, itemsField, field3, picture);
    assertEquals(0, room.getEast());
  }

  @Test
  @DisplayName("Test getWest")
  void getWest() {
    Room room = createRoom("Hallway", "1", "A long hallway.", "2", "0", "0", "0", field1, field2, itemsField, field3, picture);
    assertEquals(0, room.getWest());
  }

  @Test
  @DisplayName("Test getPuzzle")
  void getPuzzle() {
    Room room = createRoom("Room with Puzzle", "5", "A room with a locked door.", "0", "0", "0", "0", field1, field2, itemsField, field3, picture);
    Puzzle puzzle = new Puzzle("Lock", true, true, false, "key", 25, "A tricky lock.", "", "");
    room.setPuzzle(puzzle);
    assertEquals(puzzle, room.getPuzzle());
  }

  @Test
  @DisplayName("Test getMonster")
  void getMonster() {
    Room room = createRoom("Dungeon", "10", "A dark dungeon.", "0", "0", "0", "0", field1, field2, itemsField, field3, picture);
    Monster monster = new Monster("Goblin", true, 15, true, "Slash!", "Tiny and aggressive", "", 50, "Sword", "");
    room.setMonster(monster);
    assertEquals(monster, room.getMonster());
  }

  @Test
  @DisplayName("Test getItemsField")
  void getItemsField() {
    Room room = createRoomExtended("Storage", "6", "A dusty storage room.", "0", "0", "0", "0",
            "", "", "Lantern, Rope", "", "");
    assertEquals("Lantern, Rope", room.getItemsField());
  }

  @Test
  @DisplayName("Test getFixtureNames")
  void getFixtureNames() {
    Room room = createRoomExtended("Library", "4", "A library with books.", "0", "0", "0", "0",
            "", "", "", "Desk, Bookshelf", "");
    assertEquals("Desk, Bookshelf", room.getFixtureNames());
  }

  @Test
  @DisplayName("Test getPicture")
  void getPicture() {
    Room room = createRoomExtended("Gallery", "9", "An art gallery.", "0", "0", "0", "0",
            "", "", "", "", "gallery_image.png");
    assertEquals("gallery_image.png", room.getPicture());
  }

  // (Other tests for fixture list, adding/removing items, etc., are updated similarly)

  @Test
  @DisplayName("Test toString")
  void testToString() {
    Room room = createRoom("Hallway", "1", "A long hallway.", "2", "0", "0", "0", field1, field2, itemsField, field3, picture);
    assertNotNull(room.toString());
  }

  @Test
  @DisplayName("Test equals")
  void testEquals() {
    Room room1 = createRoom("Hallway", "1", "A long hallway.", "2", "0", "0", "0", field1, field2, itemsField, field3, picture);
    Room room2 = createRoom("Hallway", "1", "A long hallway.", "2", "0", "0", "0", field1, field2, itemsField, field3, picture);
    assertTrue(room1.equals(room2));
  }

  @Test
  @DisplayName("Test hashCode")
  void testHashCode() {
    Room room1 = createRoom("Hallway", "1", "A long hallway.", "2", "0", "0", "0", field1, field2, itemsField, field3, picture);
    Room room2 = createRoom("Hallway", "1", "A long hallway.", "2", "0", "0", "0", field1, field2, itemsField, field3, picture);
    assertEquals(room1.hashCode(), room2.hashCode());
  }
}
