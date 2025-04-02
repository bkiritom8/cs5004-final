package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a room in the game world.
 * Each room has a name, description, exits to other rooms, and may contain
 * items, fixtures, puzzles, and monsters.
 */
public class Room {
  private final String room_name;
  private final String room_number;
  private final String description;
  private final int N;
  private final int S;
  private final int E;
  private final int W;
  private final Map<Direction, String> exitRoomNumbers;
  private final Map<Direction, Room> exits;
  private final List<Item> items;
  private final List<Fixture> fixtures;
  private final Map<String, Fixture> fixtureMap;
  private final Map<String, Item> itemMap;
  private Puzzle puzzleObj;
  private Monster monsterObj;
  private String picture;

  /**
   * Full constructor for Room.
   *
   * @param room_name Room name
   * @param room_number Room number/ID
   * @param description Room description
   * @param exitRoomNumbers Map of exits to room numbers
   * @param field1 Unused parameter
   * @param field2 Unused parameter
   * @param itemsField Unused parameter
   * @param i Unused parameter
   * @param field3 Unused parameter
   * @param picture Path to room picture
   */
  public Room(String room_name, String room_number, String description,
              Map<Direction, String> exitRoomNumbers,
              int field1, int field2, int itemsField, int i, String field3, String picture) {
    this.room_name = room_name;
    this.room_number = room_number;
    this.description = description;
    this.exitRoomNumbers = (exitRoomNumbers != null) ? new HashMap<>(exitRoomNumbers) : new HashMap<>();
    this.N = parseExitAsInt(this.exitRoomNumbers.get(Direction.NORTH));
    this.S = parseExitAsInt(this.exitRoomNumbers.get(Direction.SOUTH));
    this.E = parseExitAsInt(this.exitRoomNumbers.get(Direction.EAST));
    this.W = parseExitAsInt(this.exitRoomNumbers.get(Direction.WEST));
    this.items = new ArrayList<>();
    this.fixtures = new ArrayList<>();
    this.exits = new HashMap<>();
    this.fixtureMap = new HashMap<>();
    this.itemMap = new HashMap<>();
    this.picture = picture;
  }

  /**
   * Constructor for Room with string parameters.
   *
   * @param room_name Room name
   * @param room_number Room number/ID
   * @param description Room description
   * @param exitRoomNumbers Map of exits to room numbers
   * @param puzzle Puzzle name/ID (nullable)
   * @param monster Monster name/ID (nullable)
   * @param itemsString Comma-separated list of item names (nullable)
   * @param fixturesString Comma-separated list of fixture names (nullable)
   * @param picture Path to room picture (nullable)
   */
  public Room(String room_name, String room_number, String description,
              Map<Direction, String> exitRoomNumbers,
              String puzzle, String monster, String itemsString, String fixturesString, String picture) {
    this.room_name = room_name;
    this.room_number = room_number;
    this.description = description;
    this.exitRoomNumbers = (exitRoomNumbers != null) ? new HashMap<>(exitRoomNumbers) : new HashMap<>();
    this.N = parseExitAsInt(this.exitRoomNumbers.get(Direction.NORTH));
    this.S = parseExitAsInt(this.exitRoomNumbers.get(Direction.SOUTH));
    this.E = parseExitAsInt(this.exitRoomNumbers.get(Direction.EAST));
    this.W = parseExitAsInt(this.exitRoomNumbers.get(Direction.WEST));
    this.items = new ArrayList<>();
    this.fixtures = new ArrayList<>();
    this.exits = new HashMap<>();
    this.fixtureMap = new HashMap<>();
    this.itemMap = new HashMap<>();
    this.picture = picture;
    // Note: Items, fixtures, puzzles, and monsters must be set separately
  }

  /**
   * Simplified constructor for testing purposes.
   *
   * @param name Room name
   * @param roomNumber Room number/ID
   * @param description Room description
   * @param exits Map of exits to room numbers
   */
  public Room(String name, String roomNumber, String description, Map<Direction, String> exits) {
    this.room_name = name;
    this.room_number = roomNumber;
    this.description = description;
    this.exitRoomNumbers = (exits != null) ? new HashMap<>(exits) : new HashMap<>();
    this.N = parseExitAsInt(this.exitRoomNumbers.get(Direction.NORTH));
    this.S = parseExitAsInt(this.exitRoomNumbers.get(Direction.SOUTH));
    this.E = parseExitAsInt(this.exitRoomNumbers.get(Direction.EAST));
    this.W = parseExitAsInt(this.exitRoomNumbers.get(Direction.WEST));
    this.items = new ArrayList<>();
    this.fixtures = new ArrayList<>();
    this.exits = new HashMap<>();
    this.fixtureMap = new HashMap<>();
    this.itemMap = new HashMap<>();
    this.picture = null;
  }

  /**
   * Parses a string to integer or returns 0 if parsing fails.
   *
   * @param value The string to parse
   * @return The parsed integer or 0
   */
  private int parseExitAsInt(String value) {
    if (value == null) return 0;
    try {
      return Integer.parseInt(value.trim());
    } catch (NumberFormatException e) {
      return 0;
    }
  }

  /**
   * Gets the name of the room.
   *
   * @return The room name
   */
  public String getRoomName() {
    return room_name;
  }

  /**
   * Gets the room number/ID.
   *
   * @return The room number
   */
  public String getRoomNumber() {
    return room_number;
  }

  /**
   * Gets the description of the room.
   *
   * @return The room description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Gets the north exit value.
   *
   * @return The north exit value
   */
  public int getNorth() {
    return N;
  }

  /**
   * Gets the south exit value.
   *
   * @return The south exit value
   */
  public int getSouth() {
    return S;
  }

  /**
   * Gets the east exit value.
   *
   * @return The east exit value
   */
  public int getEast() {
    return E;
  }

  /**
   * Gets the west exit value.
   *
   * @return The west exit value
   */
  public int getWest() {
    return W;
  }

  /**
   * Gets the list of fixtures in the room.
   *
   * @return The list of fixtures
   */
  public List<Fixture> getFixtureList() {
    return new ArrayList<>(fixtures);
  }

  /**
   * Adds a fixture to the room.
   *
   * @param fixture The fixture to add
   */
  public void addFixture(Fixture fixture) {
    if (fixture != null) {
      fixtures.add(fixture);
      fixtureMap.put(fixture.getName(), fixture);
    }
  }

  /**
   * Removes a fixture from the room.
   *
   * @param fixture The fixture to remove
   * @return true if the fixture was removed, false otherwise
   */
  public boolean removeFixture(Fixture fixture) {
    if (fixture != null) {
      fixtureMap.remove(fixture.getName());
      return fixtures.remove(fixture);
    }
    return false;
  }

  /**
   * Adds an item to the room.
   *
   * @param item The item to add
   */
  public void addItem(Item item) {
    if (item != null) {
      items.add(item);
      itemMap.put(item.getName().toUpperCase(), item);
    }
  }

  /**
   * Removes an item from the room.
   *
   * @param item The item to remove
   */
  public void removeItem(Item item) {
    if (item != null) {
      itemMap.remove(item.getName().toUpperCase());
      items.remove(item);
    }
  }

  /**
   * Clears all items from the room.
   */
  public void clearItems() {
    items.clear();
    itemMap.clear();
  }

  /**
   * Gets an item by name.
   *
   * @param name The name of the item
   * @return The item, or null if not found
   */
  public Item getItem(String name) {
    return (name != null) ? itemMap.get(name.toUpperCase()) : null;
  }

  /**
   * Gets the list of items in the room.
   *
   * @return A copy of the list of items
   */
  public List<Item> getItems() {
    return new ArrayList<>(items);
  }

  /**
   * Sets the items in this room.
   *
   * @param items List of items to set
   */
  public void setItems(List<Item> items) {
    this.items.clear();
    this.itemMap.clear();
    if (items != null) {
      for (Item item : items) {
        this.items.add(item);
        this.itemMap.put(item.getName().toUpperCase(), item);
      }
    }
  }

  /**
   * Sets the fixtures in this room.
   *
   * @param fixtures List of fixtures to set
   */
  public void setFixtures(List<Fixture> fixtures) {
    this.fixtures.clear();
    this.fixtureMap.clear();
    if (fixtures != null) {
      for (Fixture fixture : fixtures) {
        this.fixtures.add(fixture);
        this.fixtureMap.put(fixture.getName(), fixture);
      }
    }
  }

  /**
   * Sets the puzzle in the room.
   *
   * @param puzzle The puzzle to set
   */
  public void setPuzzle(Puzzle puzzle) {
    this.puzzleObj = puzzle;
  }

  /**
   * Gets the puzzle in the room.
   *
   * @return The puzzle, or null if none
   */
  public Puzzle getPuzzle() {
    return puzzleObj;
  }

  /**
   * Sets the monster in the room.
   *
   * @param monster The monster to set
   */
  public void setMonster(Monster monster) {
    this.monsterObj = monster;
  }

  /**
   * Gets the monster in the room.
   *
   * @return The monster, or null if none
   */
  public Monster getMonster() {
    return monsterObj;
  }

  /**
   * Sets an exit to another room.
   *
   * @param direction The direction of the exit
   * @param neighbor The room the exit leads to
   */
  public void setExit(Direction direction, Room neighbor) {
    if (direction != null && neighbor != null) {
      exits.put(direction, neighbor);
    }
  }

  /**
   * Gets the room that an exit leads to.
   *
   * @param direction The direction of the exit
   * @return The room the exit leads to, or null if none
   */
  public Room getExit(Direction direction) {
    return exits.get(direction);
  }

  /**
   * Sets the room number for an exit.
   *
   * @param direction The direction of the exit
   * @param number The room number the exit leads to
   */
  public void setExitRoomNumber(Direction direction, String number) {
    if (direction != null && number != null) {
      exitRoomNumbers.put(direction, number);
    }
  }

  /**
   * Gets the room number for an exit.
   *
   * @param direction The direction of the exit
   * @return The room number the exit leads to, or "0" if none
   */
  public String getExitRoomNumber(Direction direction) {
    return exitRoomNumbers.getOrDefault(direction, "0");
  }

  /**
   * Gets the name of the room.
   *
   * @return The room name
   */
  public String getName() {
    return room_name;
  }

  /**
   * Gets a fixture by name.
   *
   * @param target The name of the fixture
   * @return The fixture, or null if not found
   */
  public Fixture getFixture(String target) {
    return (target != null) ? fixtureMap.get(target) : null;
  }

  @Override
  public String toString() {
    return "Room [room_name=" + room_name
            + ", room_number=" + room_number
            + ", description=" + description
            + ", N=" + N + ", S=" + S + ", E=" + E + ", W=" + W
            + ", puzzle=" + puzzleObj + ", monster=" + monsterObj
            + ", items=" + items + ", fixtures=" + fixtures
            + ", picture=" + picture + "]";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Room room)) return false;
    return room_number.equals(room.room_number)
            && room_name.equals(room.room_name)
            && description.equals(room.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(room_name, room_number, description);
  }
}