package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Room {
  private String room_name;
  private int room_number;
  private String description;
  private int N, S, E, W;
  private String puzzle;     // puzzle name from JSON
  private String monster;    // monster name from JSON
  private String items;
  private String fixtures;
  private String picture;

  private List<Fixture> fixtureList = new ArrayList<>();
  private List<Item> itemList = new ArrayList<>();
  private Puzzle puzzleObj;
  private Monster monsterObj;
  private Map<Direction, Room> exits = new HashMap<>();
  private Map<Direction, String> exitRoomNumbers = new HashMap<>();

  public Room(String roomName, String roomNumber, String description, Map<Direction, String> exits) {}

  public Room(String room_name, int room_number, String description, int N, int S, int E, int W) {
    this(room_name, room_number, description, N, S, E, W, "", "", "", "", "");
  }

  public Room(String room_name, int room_number, String description, int N, int S, int E, int W,
              String puzzle, String monster, String items, String fixtures, String picture) {
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

  // Getters
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

  // Fixture list
  public List<Fixture> getFixtureList() { return fixtureList; }
  public void addFixture(Fixture fixture) { fixtureList.add(fixture); }
  public boolean removeFixture(Fixture fixture) { return fixtureList.remove(fixture); }

  // Item list
  public void addItem(Item item) { itemList.add(item); }
  public void removeItem(Item item) { itemList.remove(item); }
  public Item getItem(String name) {
    for (Item item : itemList) {
      if (item.getName().equalsIgnoreCase(name)) {
        return item;
      }
    }
    return null;
  }
  public List<Item> getItems() { return itemList; }
  public void clearItems() { itemList.clear(); }

  // Puzzle and Monster object assignment
  public void setPuzzle(Puzzle puzzle) { this.puzzleObj = puzzle; }
  public Puzzle getPuzzle() { return puzzleObj; }

  public void setMonster(Monster monster) { this.monsterObj = monster; }
  public Monster getMonster() { return monsterObj; }

  // Exit connections
  public void setExit(Direction direction, Room neighbor) {
    exits.put(direction, neighbor);
  }

  public Room getExit(Direction direction) {
    return exits.get(direction);
  }

  public void setExitRoomNumber(Direction direction, String number) {
    exitRoomNumbers.put(direction, number);
  }

  public String getExitRoomNumber(Direction direction) {
    return exitRoomNumbers.getOrDefault(direction, "0");
  }

  @Override
  public String toString() {
    return "Room [room_name=" + room_name + ", room_number=" + room_number +
            ", description=" + description + ", N=" + N + ", S=" + S + ", E=" + E +
            ", W=" + W + ", puzzle=" + puzzle + ", monster=" + monster +
            ", items=" + items + ", fixtures=" + fixtures + ", picture=" + picture + "]";
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

  public String getName() {
    return null;
  }
}