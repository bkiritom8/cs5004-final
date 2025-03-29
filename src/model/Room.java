package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/** Represents a room in the game world. */
public class Room {
  private String room_name;
  private String room_number;
  private String description;
  private int N, S, E, W;
  private Map<Direction, String> exitRoomNumbers;
  private Map<Direction, Room> exits;
  private List<Item> items;
  private List<Fixture> fixtures;
  private Map<String, Fixture> fixtureMap;
  private Map<String, Item> itemMap;
  private Puzzle puzzleObj;
  private Monster monsterObj;
  private String picture;

  // NEW constructor as per your request
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
    this.W = 0; // default W value
    this.items = new ArrayList<>();
    this.fixtures = new ArrayList<>();
    this.exits = new HashMap<>();
    this.fixtureMap = new HashMap<>();
    this.itemMap = new HashMap<>();
    this.picture = null; // or assign field3 if it's intended to be picture
    // Optionally you could use itemsField and field3 for initial setup if needed
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

  public Room(String roomName, String roomNumber, String description, Map<Direction, String> exits) {
  }

  /** Returns the room's name. */
  public String getRoomName() { return room_name; }

  public String getRoomNumber() { return room_number; }

  public String getDescription() { return description; }

  public int getNorth() { return N; }

  public int getSouth() { return S; }

  public int getEast() { return E; }

  public int getWest() { return W; }

  public List<Fixture> getFixtureList() { return fixtures; }

  public void addFixture(Fixture fixture) {
    if (fixture != null) {
      fixtures.add(fixture);
      fixtureMap.put(fixture.getName(), fixture);
    }
  }

  public boolean removeFixture(Fixture fixture) {
    if (fixture != null) {
      fixtureMap.remove(fixture.getName());
      return fixtures.remove(fixture);
    }
    return false;
  }

  public void addItem(Item item) {
    if (item != null) {
      items.add(item);
      itemMap.put(item.getName().toUpperCase(), item);
    }
  }

  public void removeItem(Item item) {
    if (item != null) {
      itemMap.remove(item.getName());
      items.remove(item);
    }
  }

  public void clearItems() {
    items.clear();
    itemMap.clear();
  }

  public Item getItem(String name) {
    return itemMap.get(name.toUpperCase());
  }

  public List<Item> getItems() {
    return new ArrayList<>(items);
  }

  public void setPuzzle(Puzzle puzzle) { this.puzzleObj = puzzle; }

  public Puzzle getPuzzle() { return puzzleObj; }

  public void setMonster(Monster monster) { this.monsterObj = monster; }

  public Monster getMonster() { return monsterObj; }

  public void setExit(Direction direction, Room neighbor) {
    if (direction != null && neighbor != null) {
      exits.put(direction, neighbor);
    }
  }

  public Room getExit(Direction direction) {
    return exits.get(direction);
  }

  public void setExitRoomNumber(Direction direction, String number) {
    if (direction != null && number != null) {
      exitRoomNumbers.put(direction, number);
    }
  }

  public String getExitRoomNumber(Direction direction) {
    return exitRoomNumbers.getOrDefault(direction, "0");
  }

  public String getName() {
    return room_name;
  }

  public Fixture getFixture(String target) {
    return fixtureMap.get(target);
  }

  @Override
  public String toString() {
    return "Room [room_name=" + room_name + ", room_number=" + room_number +
            ", description=" + description + ", N=" + N + ", S=" + S + ", E=" + E +
            ", W=" + W + ", puzzle=" + puzzleObj + ", monster=" + monsterObj +
            ", items=" + items + ", fixtures=" + fixtures +
            ", picture=" + picture + "]";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Room)) return false;
    Room room = (Room) o;
    return room_number.equals(room.room_number) &&
            room_name.equals(room.room_name) &&
            description.equals(room.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(room_name, room_number, description);
  }

  private int parseExitAsInt(String value) {
    if (value == null) return 0;
    try {
      return Integer.parseInt(value.trim());
    } catch (NumberFormatException e) {
      return 0;
    }
  }

  public void setDescription(String s) {
  }

  public void setItems(ArrayList<Object> objects) {
  }

  public void setFixtures(ArrayList<Object> objects) {
  }

  public void addMonster(Monster roomMonster) {
  }

  public boolean getMonsters() {
    return false;
  }
}
