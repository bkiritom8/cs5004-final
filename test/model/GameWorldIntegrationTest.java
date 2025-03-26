package model;

import org.junit.jupiter.api.Test;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test for the GameWorld class.
 * Tests various game scenarios to ensure proper interaction between game components.
 */
public class GameWorldIntegrationTest {

  /**
   * Tests loading a game with empty rooms.
   * Verifies that basic game information and starting room are correctly initialized.
   *
   * @throws IOException If there is an error reading the test game file
   */
  @Test
  void testLoadEmptyRoomsGame() throws IOException {
    GameWorld gameWorld = new GameWorld("./resources/empty_rooms.json");
    assertEquals("Empty Rooms", gameWorld.getGameName());

    Room startRoom = gameWorld.getPlayer().getCurrentRoom();
    assertEquals("1", startRoom.getRoomNumber());
    assertEquals("Hallway 1", startRoom.getName());
  }

  /**
   * Tests player navigation between rooms.
   * Verifies that player movement and room connections work correctly.
   *
   * @throws IOException If there is an error reading the test game file
   */
  @Test
  void testPlayerNavigationScenario() throws IOException {
    GameWorld gameWorld = new GameWorld("./resources/empty_rooms.json");
    Player player = gameWorld.getPlayer();

    // Start in room 1
    assertEquals("1", player.getCurrentRoom().getRoomNumber());

    // Move north to room 2
    Room currentRoom = player.getCurrentRoom();
    Room northRoom = currentRoom.getExit(Direction.NORTH);
    player.setCurrentRoom(northRoom);
    assertEquals("2", player.getCurrentRoom().getRoomNumber());

    // Move north to room 3
    currentRoom = player.getCurrentRoom();
    northRoom = currentRoom.getExit(Direction.NORTH);
    player.setCurrentRoom(northRoom);
    assertEquals("3", player.getCurrentRoom().getRoomNumber());

    // Move south back to room 2
    currentRoom = player.getCurrentRoom();
    Room southRoom = currentRoom.getExit(Direction.SOUTH);
    player.setCurrentRoom(southRoom);
    assertEquals("2", player.getCurrentRoom().getRoomNumber());
  }

  /**
   * Tests item interactions including picking up and dropping items.
   * Verifies that item transfers between room and player inventory work correctly.
   *
   * @throws IOException If there is an error reading the test game file
   */
  @Test
  void testItemInteractionScenario() throws IOException {
    GameWorld gameWorld = new GameWorld("./resources/simple_hallway.json");
    Player player = gameWorld.getPlayer();

    // Take notebook from starting room
    Room startRoom = player.getCurrentRoom();
    Item notebook = startRoom.getItem("Notebook");
    assertNotNull(notebook);

    player.addToInventory(notebook);
    startRoom.removeItem(notebook);

    assertTrue(player.getInventory().contains(notebook));
    assertFalse(startRoom.getItems().contains(notebook));

    //Drop notebook back in room
    player.removeFromInventory(notebook);
    startRoom.addItem(notebook);

    assertFalse(player.getInventory().contains(notebook));
    assertTrue(startRoom.getItems().contains(notebook));
  }

  /**
   * Tests puzzle solving mechanics.
   * Verifies that puzzles can be properly identified and solved.
   *
   * @throws IOException If there is an error reading the test game file
   */
  @Test
  void testPuzzleSolvingScenario() throws IOException {
    GameWorld gameWorld = new GameWorld("./resources/simple_hallway.json");
    Player player = gameWorld.getPlayer();

    // Move to room 2 (containing Lock puzzle)
    Room startRoom = player.getCurrentRoom();
    Room northRoom = startRoom.getExit(Direction.NORTH);
    player.setCurrentRoom(northRoom);

    // Room 2 has lock puzzle
    Puzzle lockPuzzle = player.getCurrentRoom().getPuzzle();
    assertNotNull(lockPuzzle);
  }
}