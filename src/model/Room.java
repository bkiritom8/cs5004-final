package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a room in the game, containing directional exits, items, puzzles, and monsters.
 */
public class Room {
  private String room_name;
  private int room_number;
  private String description;
  private int N, S, E, W;
  private String puzzle;
  private String monster;
  private String items;
  private String fixtures;
  private String picture;

  private List<Fixture> fixtureList = new ArrayList<>();
  private List<Item> itemList = new ArrayList<>();
  private Puzzle puzzleObj;
  private Monster monsterObj;
  private Map<Direction, Room> exits = new HashMap<>();
  private Map<Direction, String> exitRoomNumbers = new HashMap<>();

  /**
   * Placeholder constructor not implemented.
   */
  public Room(String roomName, String roomNumber, String description,
              Map<Direction, String> exits, int field1, int field2,
              int itemsField, String field3, String picture) {}

  /**
   * Constructs a basic room with directional links and description.
   */
  public Room(String room_name, int room_number, String description,
              int N, int S, int E, int W) {
    this(room_name, room_number, description, N, S, E, W, "", "", "", "", "");
  }

  /**
   * Constructs a room with all fields including puzzle, monster, items, and fixtures.
   */
  public Room(String room_name, int room_number, String description,
              int N, int S, int E, int W, String puzzle, String monster,
              String items, String fixtures, String picture) {
    this.room_name = room_name;
    this.room_number = room_number;
    this.description = description;
    this.N = N;
    this.S = S;
    this.E = E;
    this.W = W;
    this.puzzle = puzzle;
    this.monster = monster;
    this.items = items;
    this.fixtures = fixtures;
    this.picture = picture;
  }

  /**
   * Placeholder constructor not implemented.
   */
  public Room(String roomName, String roomNumber, String description,
              Map<Direction, String> exits, int field1, int field2,
              int itemsField, int field3, int picture) {}

  public Room(String name, String roomNumber, String description, Map<Direction, String> exits, String field1, String field2, String itemsField, String field3, String picture) {
  }

  public String getRoomName() { return room_name; }
  public int getRoomNumber() { return room_number; }
  public String getDescription() { return description; }
  public int getNorth() { return N; }
  public int getSouth() { return S; }
  public int getEast() { return E; }
  public int getWest() { return W; }
  public String getItemsField() { return items; }
  public String getFixtureNames() { return fixtures; }
  public String getPicture() { return picture; }

  /**
   * Returns a list of fixtures in the room.
   */
  public List<Fixture> getFixtureList() { return fixtureList; }

  /**
   * Adds a fixture to the room.
   */
  public void addFixture(Fixture fixture) { fixtureList.add(fixture); }

  /**
   * Removes a fixture from the room.
   */
  public boolean removeFixture(Fixture fixture) { return fixtureList.remove(fixture); }

  /**
   * Adds an item to the room.
   */
  public void addItem(Item item) { itemList.add(item); }

  /**
   * Removes an item from the room.
   */
  public void removeItem(Item item) { itemList.remove(item); }

  /**
   * Gets an item by name from the room.
   */
  public Item getItem(String name) {
    for (Item item : itemList) {
      if (item.getName().equalsIgnoreCase(name)) {
        return item;
      }
    }
    return null;
  }

  /**
   * Returns the list of items in the room.
   */
  public List<Item> getItems() { return itemList; }

  /**
   * Removes all items from the room.
   */
  public void clearItems() { itemList.clear(); }

  /**
   * Sets the puzzle object in the room.
   */
  public void setPuzzle(Puzzle puzzle) { this.puzzleObj = puzzle; }

  /**
   * Returns the puzzle object in the room.
   */
  public Puzzle getPuzzle() { return puzzleObj; }

  /**
   * Sets the monster object in the room.
   */
  public void setMonster(Monster monster) { this.monsterObj = monster; }

  /**
   * Returns the monster object in the room.
   */
  public Monster getMonster() { return monsterObj; }

  /**
   * Sets an exit from this room in a given direction.
   */
  public void setExit(Direction direction, Room neighbor) {
    exits.put(direction, neighbor);
  }

  /**
   * Gets the connected room in a given direction.
   */
  public Room getExit(Direction direction) {
    return exits.get(direction);
  }

  /**
   * Sets the room number string for an exit in a given direction.
   */
  public void setExitRoomNumber(Direction direction, String number) {
    exitRoomNumbers.put(direction, number);
  }

  /**
   * Returns the room number string of an exit in a given direction.
   */
  public String getExitRoomNumber(Direction direction) {
    return exitRoomNumbers.getOrDefault(direction, "0");
  }

  @Override
  public String toString() {
    return "Room [room_name=" + room_name + ", room_number=" + room_number +
            ", description=" + description + ", N=" + N + ", S=" + S + ", E=" + E +
            ", W=" + W + ", puzzle=" + puzzle + ", monster=" + monster +
            ", items=" + items + ", fixtures=" + fixtures +
            ", picture=" + picture + "]";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Room)) return false;
    Room room = (Room) o;
    return room_number == room.room_number &&
            N == room.N && S == room.S && E == room.E && W == room.W &&
            Objects.equals(room_name, room.room_name) &&
            Objects.equals(description, room.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(room_name, room_number, description, N, S, E, W);
  }

  /**
   * Placeholder method. Not implemented.
   */
  public String getName() {
    return null;
  }

  /**
   * Placeholder method for getting a fixture by name. Not implemented.
   */
  public Fixture getFixture(String target) {
    return null;
  }
}
