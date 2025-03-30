package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/** Represents a room in the game world. */
public class Room {
  private final String room_name;
  private final String room_number;
  private final String description;
  private final int N;
  private final int S;
  private final int E;
  private int W;
  private final Map<Direction, String> exitRoomNumbers;
  private final Map<Direction, Room> exits;
  private final List<Item> items;
  private final List<Fixture> fixtures;
  private final Map<String, Fixture> fixtureMap;
  private final Map<String, Item> itemMap;
  private Puzzle puzzleObj;
  private Monster monsterObj;
  private String picture;

  public Room(String room_name, String room_number, String description,
              Map<Direction, String> exitRoomNumbers,
              int N, int S, int E,
              String itemsField, String field3) {
    this.room_name = room_name;
    this.room_number = room_number;
    this.description = description;
    this.exitRoomNumbers = (exitRoomNumbers != null) ? exitRoomNumbers : new HashMap<>();
    this.N = N;
    this.S = S;
    this.E = E;
    this.W = W;
    this.items = new ArrayList<>();
    this.fixtures = new ArrayList<>();
    this.exits = new HashMap<>();
    this.fixtureMap = new HashMap<>();
    this.itemMap = new HashMap<>();
    this.picture = picture;
  }

  public Room(String room_name, String room_number, String description,
              Map<Direction, String> exitRoomNumbers,
              String field1, String field2, String itemsField, String field3, String picture) {
    this.room_name = room_name;
    this.room_number = room_number;
    this.description = description;
    this.exitRoomNumbers = (exitRoomNumbers != null) ? exitRoomNumbers : new HashMap<>();
    this.items = new ArrayList<>();
    this.fixtures = new ArrayList<>();
    this.exits = new HashMap<>();
    this.fixtureMap = new HashMap<>();
    this.itemMap = new HashMap<>();
    this.N = parseExitAsInt(this.exitRoomNumbers.get(Direction.NORTH));
    this.S = parseExitAsInt(this.exitRoomNumbers.get(Direction.SOUTH));
    this.E = parseExitAsInt(this.exitRoomNumbers.get(Direction.EAST));
    this.W = parseExitAsInt(this.exitRoomNumbers.get(Direction.WEST));
    this.picture = picture;
  }

  /** Returns the room's name. */
  /** TODO: Add method documentation. */
  public String getRoomName() { return room_name; }
  /** Returns the room's number. */
  /** TODO: Add method documentation. */
  public String getRoomNumber() { return room_number; }
  /** Returns the room description. */
  /** TODO: Add method documentation. */
  public String getDescription() { return description; }
  /** Returns the north exit value. */
  /** TODO: Add method documentation. */
  public int getNorth() { return N; }
  /** Returns the south exit value. */
  /** TODO: Add method documentation. */
  public int getSouth() { return S; }
  /** Returns the east exit value. */
  /** TODO: Add method documentation. */
  public int getEast() { return E; }
  /** Returns the west exit value. */
  /** TODO: Add method documentation. */
  public int getWest() { return W; }

  /** Returns list of all fixtures. */
  /** TODO: Add method documentation. */
  public List<Fixture> getFixtureList() { return fixtures; }

  /** Adds a fixture to the room. */
  /** TODO: Add method documentation. */
  public void addFixture(Fixture fixture) {
    if (fixture != null) {
      fixtures.add(fixture);
      fixtureMap.put(fixture.getName(), fixture);
    }
  }

  /** Removes a fixture. */
  /** TODO: Add method documentation. */
  public boolean removeFixture(Fixture fixture) {
    if (fixture != null) {
      fixtureMap.remove(fixture.getName());
      return fixtures.remove(fixture);
    }
    return false;
  }

  /** Adds an item to the room. */
  /** TODO: Add method documentation. */
  public void addItem(Item item) {
    if (item != null) {
      items.add(item);
      itemMap.put(item.getName().toUpperCase(), item);
    }
  }

  /** Removes an item from the room. */
  /** TODO: Add method documentation. */
  public void removeItem(Item item) {
    if (item != null) {
      itemMap.remove(item.getName());
      items.remove(item);
    }
  }

  /** Clears all items in the room. */
  /** TODO: Add method documentation. */
  public void clearItems() {
    items.clear();
    itemMap.clear();
  }

  /** Gets an item by name. */
  /** TODO: Add method documentation. */
  public Item getItem(String name) {
    return itemMap.get(name.toUpperCase());
  }

  /** Returns a list of items. */
  /** TODO: Add method documentation. */
  public List<Item> getItems() {
    return new ArrayList<>(items);
  }

  /** Sets the room's puzzle. */
  /** TODO: Add method documentation. */
  public void setPuzzle(Puzzle puzzle) { this.puzzleObj = puzzle; }
  /** Gets the room's puzzle. */
  /** TODO: Add method documentation. */
  public Puzzle getPuzzle() { return puzzleObj; }

  /** Sets the room's monster. */
  /** TODO: Add method documentation. */
  public void setMonster(Monster monster) { this.monsterObj = monster; }
  /** Gets the room's monster. */
  /** TODO: Add method documentation. */
  public Monster getMonster() { return monsterObj; }
  /** TODO: Add method documentation. */
  public void setExit(Direction direction, Room neighbor) {
    if (direction != null && neighbor != null) {
      exits.put(direction, neighbor);
    }
  }

  /** TODO: Add method documentation. */
  public Room getExit(Direction direction) {
    return exits.get(direction);
  }

  /** TODO: Add method documentation. */
  public void setExitRoomNumber(Direction direction, String number) {
    if (direction != null && number != null) {
      exitRoomNumbers.put(direction, number);
    }
  }

  /** TODO: Add method documentation. */
  public String getExitRoomNumber(Direction direction) {
    return exitRoomNumbers.getOrDefault(direction, "0");
  }

  /** TODO: Add method documentation. */
  public String getName() {
    return room_name;
  }

  /** Gets fixture by name. */
  /** TODO: Add method documentation. */
  public Fixture getFixture(String target) {
    return fixtureMap.get(target);
  }

  @Override
  public String toString() {
    return "Room [room_name="
            + room_name
            + ", room_number="
            + room_number
            + ", description="
            + description
            + ", N="
            + N
            + ", S="
            + S
            + ", E="
            + E
            + ", W="
            + W
            + ", puzzle="
            + puzzleObj
            + ", monster="
            + monsterObj
            + ", items="
            + items
            + ", fixtures="
            + fixtures
            + ", picture="
            + picture + "]";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Room room)) return false;
    return room_number.equals(room.room_number) &&
            room_name.equals(room.room_name) &&
            description.equals(room.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(room_name, room_number, description);
  }

  /** Parses string to int or returns 0. */
  private int parseExitAsInt(String value) {
    if (value == null) {
      return 0;
    }
    try {
      return Integer.parseInt(value.trim());
    } catch (NumberFormatException e) {
      return 0;
    }
  }
}