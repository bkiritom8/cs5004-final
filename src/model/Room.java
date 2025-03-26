package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a room in the adventure game.
 * A room has a name, number, a description, directional navigation values,
 * and optionally a list of fixtures (which in our JSON data are provided as a comma-separated string).
 *
 * The navigation fields (N, S, E, W) are interpreted as follows:
 *  - 0 indicates a wall or blocked path.
 *  - A positive number indicates the room number connected in that direction.
 *  - A negative number indicates that a puzzle or monster is blocking the way.
 */
public class Room {
  private String roomName;
  private int roomNumber;
  private String description;
  private int north;
  private int south;
  private int east;
  private int west;
  private List<Fixture> fixtures;

  /**
   * Constructs a Room with the required attributes.
   *
   * @param roomName    the room's name
   * @param roomNumber  the room number
   * @param description the room description
   * @param north       value for navigation to the north
   * @param south       value for navigation to the south
   * @param east        value for navigation to the east
   * @param west        value for navigation to the west
   */
  public Room(String roomName, int roomNumber, String description, int north, int south, int east, int west) {
    this.roomName = roomName;
    this.roomNumber = roomNumber;
    this.description = description;
    this.north = north;
    this.south = south;
    this.east = east;
    this.west = west;
    this.fixtures = new ArrayList<>();
  }

  public String getRoomName() {
    return roomName;
  }

  public int getRoomNumber() {
    return roomNumber;
  }

  public String getDescription() {
    return description;
  }

  public int getNorth() {
    return north;
  }

  public int getSouth() {
    return south;
  }

  public int getEast() {
    return east;
  }

  public int getWest() {
    return west;
  }

  public List<Fixture> getFixtures() {
    return fixtures;
  }

  /**
   * Adds a fixture to the room.
   *
   * @param fixture the fixture to add
   */
  public void addFixture(Fixture fixture) {
    fixtures.add(fixture);
  }

  /**
   * Removes a fixture from the room.
   *
   * @param fixture the fixture to remove
   * @return true if the fixture was removed, false otherwise
   */
  public boolean removeFixture(Fixture fixture) {
    return fixtures.remove(fixture);
  }

  @Override
  public String toString() {
    return "Room [roomName="
            + roomName
            + ", roomNumber="
            + roomNumber
            + ", description="
            + description
            + ", N=" + north
            + ", S=" + south
            + ", E=" + east
            + ", W=" + west
            + ", fixtures="
            + fixtures + "]";
  }
}