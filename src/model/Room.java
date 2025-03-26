package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a room in the adventure game.
 * The fields correspond to the JSON attributes provided in your game data files.
 *
 * Navigation:
 *   - A zero (0) means the path is blocked.
 *   - A positive number indicates a connected room.
 *   - A negative number indicates a path blocked by a puzzle/monster.
 *
 * The fields puzzle, monster, items, fixtures, and picture are stored as strings
 * (matching the JSON file); note that for fixtures, the JSON file uses a comma‐separated
 * list of fixture names.
 */
public class Room {
  private String room_name;
  private int room_number;
  private String description;
  private int N;
  private int S;
  private int E;
  private int W;
  private String puzzle;
  private String monster;
  private String items;
  private String fixtures; // JSON field: comma-separated fixture names
  private String picture;

  // Runtime list of Fixture objects assigned to this room.
  private List<Fixture> fixtureList = new ArrayList<>();

  // Default constructor for JSON deserialization
  public Room() {}

  /**
   * Full constructor.
   */
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

  /**
   * Overloaded constructor for basic room creation.
   * Initializes puzzle, monster, items, fixtures, and picture to empty strings.
   */
  public Room(String room_name, int room_number, String description, int N, int S, int E, int W) {
    this(room_name, room_number, description, N, S, E, W, "", "", "", "", "");
  }

  // Getters
  public String getRoomName() {
    return room_name;
  }

  public int getRoomNumber() {
    return room_number;
  }

  public String getDescription() {
    return description;
  }

  public int getNorth() {
    return N;
  }

  public int getSouth() {
    return S;
  }

  public int getEast() {
    return E;
  }

  public int getWest() {
    return W;
  }

  public String getPuzzle() {
    return puzzle;
  }

  public String getMonster() {
    return monster;
  }

  public String getItems() {
    return items;
  }

  /**
   * Returns the JSON string field for fixture names.
   */
  public String getFixtureNames() {
    return fixtures;
  }

  public String getPicture() {
    return picture;
  }

  /**
   * Returns the runtime list of Fixture objects added to this room.
   */
  public List<Fixture> getFixtureList() {
    return fixtureList;
  }

  /**
   * Adds a Fixture object to this room's runtime list.
   */
  public void addFixture(Fixture fixture) {
    fixtureList.add(fixture);
  }

  /**
   * Removes a Fixture object from the room.
   *
   * @param fixture the fixture to remove
   * @return true if removed; false otherwise
   */
  public boolean removeFixture(Fixture fixture) {
    return fixtureList.remove(fixture);
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
    return room_number == room.room_number
            && N == room.N
            && S == room.S
            && E == room.E
            && W == room.W
            && Objects.equals(room_name, room.room_name)
            && Objects.equals(description, room.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(room_name, room_number, description, N, S, E, W);
  }
}
