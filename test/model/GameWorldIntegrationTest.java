package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test for the GameWorld class.
 * Tests various game scenarios to ensure proper interaction between game components.
 * Updated for HW9 - Tests model components in isolation and with the new MVC architecture.
 */
@DisplayName("Game World Integration Tests")
public class GameWorldIntegrationTest {

  private GameWorld gameWorld;
  @TempDir
  Path tempDir; // JUnit will create and clean up this directory

  /**
   * Creates a simple temporary game file for testing.
   *
   * @return Path to the created temporary file
   * @throws IOException If there's an error creating the test file
   */
  private String createTestGameFile() throws IOException {
    File gameFile = tempDir.resolve("test_game.json").toFile();

    try (FileWriter writer = new FileWriter(gameFile)) {
      writer.write("{\n"
              + "  \"name\": \"Test Adventure\",\n"
              + "  \"version\": \"1.0\",\n"
              + "  \"rooms\": [\n"
              + "    {\n"
              + "      \"room_name\": \"Start Room\",\n"
              + "      \"room_number\": \"1\",\n"
              + "      \"description\": \"A simple starting room.\",\n"
              + "      \"N\": \"2\", \"S\": \"0\", \"E\": \"0\", \"W\": \"0\",\n"
              + "      \"items\": \"Test Item\"\n"
              + "    },\n"
              + "    {\n"
              + "      \"room_name\": \"Puzzle Room\",\n"
              + "      \"room_number\": \"2\",\n"
              + "      \"description\": \"A room with a puzzle.\",\n"
              + "      \"N\": \"-3\", \"S\": \"1\", \"E\": \"0\", \"W\": \"0\",\n"
              + "      \"puzzle\": \"Door Puzzle\"\n"
              + "    },\n"
              + "    {\n"
              + "      \"room_name\": \"Monster Room\",\n"
              + "      \"room_number\": \"3\",\n"
              + "      \"description\": \"A room with a monster.\",\n"
              + "      \"N\": \"4\", \"S\": \"2\", \"E\": \"0\", \"W\": \"0\",\n"
              + "      \"monster\": \"Test Monster\"\n"
              + "    },\n"
              + "    {\n"
              + "      \"room_name\": \"Treasure Room\",\n"
              + "      \"room_number\": \"4\",\n"
              + "      \"description\": \"A room with treasure.\",\n"
              + "      \"N\": \"0\", \"S\": \"3\", \"E\": \"0\", \"W\": \"0\",\n"
              + "      \"items\": \"Treasure\"\n"
              + "    }\n"
              + "  ],\n"
              + "  \"items\": [\n"
              + "    {\n"
              + "      \"name\": \"Test Item\",\n"
              + "      \"weight\": \"1\",\n"
              + "      \"max_uses\": \"1\",\n"
              + "      \"uses_remaining\": \"1\",\n"
              + "      \"value\": \"10\",\n"
              + "      \"when_used\": \"You use the test item.\",\n"
              + "      \"description\": \"A test item for testing.\"\n"
              + "    },\n"
              + "    {\n"
              + "      \"name\": \"Puzzle Key\",\n"
              + "      \"weight\": \"1\",\n"
              + "      \"max_uses\": \"1\",\n"
              + "      \"uses_remaining\": \"1\",\n"
              + "      \"value\": \"20\",\n"
              + "      \"when_used\": \"The key unlocks the door.\",\n"
              + "      \"description\": \"A key that unlocks puzzles.\"\n"
              + "    },\n"
              + "    {\n"
              + "      \"name\": \"Monster Sword\",\n"
              + "      \"weight\": \"3\",\n"
              + "      \"max_uses\": \"3\",\n"
              + "      \"uses_remaining\": \"3\",\n"
              + "      \"value\": \"30\",\n"
              + "      \"when_used\": \"You swing the sword at the monster.\",\n"
              + "      \"description\": \"A sword for fighting monsters.\"\n"
              + "    },\n"
              + "    {\n"
              + "      \"name\": \"Treasure\",\n"
              + "      \"weight\": \"5\",\n"
              + "      \"max_uses\": \"1\",\n"
              + "      \"uses_remaining\": \"1\",\n"
              + "      \"value\": \"100\",\n"
              + "      \"when_used\": \"You examine the valuable treasure.\",\n"
              + "      \"description\": \"A valuable treasure.\"\n"
              + "    }\n"
              + "  ],\n"
              + "  \"puzzles\": [\n"
              + "    {\n"
              + "      \"name\": \"Door Puzzle\",\n"
              + "      \"active\": \"true\",\n"
              + "      \"affects_target\": \"true\",\n"
              + "      \"affects_player\": \"false\",\n"
              + "      \"solution\": \"Puzzle Key\",\n"
              + "      \"value\": \"50\",\n"
              + "      \"description\": \"A locked door blocking your path.\",\n"
              + "      \"effects\": \"The door is locked and requires a key.\",\n"
              + "      \"target\": \"2:Puzzle Room\"\n"
              + "    }\n"
              + "  ],\n"
              + "  \"monsters\": [\n"
              + "    {\n"
              + "      \"name\": \"Test Monster\",\n"
              + "      \"active\": \"true\",\n"
              + "      \"damage\": \"10\",\n"
              + "      \"can_attack\": \"true\",\n"
              + "      \"attack_description\": \"The monster attacks you!\",\n"
              + "      \"description\": \"A scary test monster.\",\n"
              + "      \"effects\": \"The monster blocks your path.\",\n"
              + "      \"value\": \"75\",\n"
              + "      \"solution\": \"Monster Sword\",\n"
              + "      \"target\": \"3:Monster Room\"\n"
              + "    }\n"
              + "  ]\n"
              + "}");
    }

    return gameFile.getAbsolutePath();
  }

  /**
   * Creates a test save game file for testing the save/load functionality.
   *
   * @return Path to the created temporary save file
   * @throws IOException If there's an error creating the save file
   */
  private String createTestSaveFile() throws IOException {
    File saveFile = tempDir.resolve("test_save.json").toFile();

    try (FileWriter writer = new FileWriter(saveFile)) {
      writer.write("{\n"
              + "  \"game_name\": \"Test Adventure\",\n"
              + "  \"version\": \"1.0\",\n"
              + "  \"player\": {\n"
              + "    \"name\": \"TestPlayer\",\n"
              + "    \"health\": 90,\n"
              + "    \"score\": 100,\n"
              + "    \"current_room\": \"3\",\n"
              + "    \"inventory\": [\n"
              + "      {\n"
              + "        \"name\": \"Monster Sword\",\n"
              + "        \"uses_remaining\": 3\n"
              + "      }\n"
              + "    ]\n"
              + "  },\n"
              + "  \"rooms\": [\n"
              + "    {\n"
              + "      \"room_number\": \"1\",\n"
              + "      \"exits\": {\n"
              + "        \"NORTH\": \"2\",\n"
              + "        \"SOUTH\": \"0\",\n"
              + "        \"EAST\": \"0\",\n"
              + "        \"WEST\": \"0\"\n"
              + "      }\n"
              + "    },\n"
              + "    {\n"
              + "      \"room_number\": \"2\",\n"
              + "      \"puzzle_active\": false,\n"
              + "      \"exits\": {\n"
              + "        \"NORTH\": \"3\",\n"
              + "        \"SOUTH\": \"1\",\n"
              + "        \"EAST\": \"0\",\n"
              + "        \"WEST\": \"0\"\n"
              + "      }\n"
              + "    },\n"
              + "    {\n"
              + "      \"room_number\": \"3\",\n"
              + "      \"monster_active\": true,\n"
              + "      \"exits\": {\n"
              + "        \"NORTH\": \"4\",\n"
              + "        \"SOUTH\": \"2\",\n"
              + "        \"EAST\": \"0\",\n"
              + "        \"WEST\": \"0\"\n"
              + "      }\n"
              + "    }\n"
              + "  ]\n"
              + "}");
    }

    return saveFile.getAbsolutePath();
  }

  @BeforeEach
  void setUp() throws IOException {
    String gameFilePath = createTestGameFile();
    gameWorld = new GameWorld(gameFilePath);
  }

  @Test
  @DisplayName("Should load game data correctly")
  void testLoadGameData() {
    assertEquals("Test Adventure", gameWorld.getGameName());

    Player player = gameWorld.getPlayer();
    assertNotNull(player);

    Room startRoom = player.getCurrentRoom();
    assertEquals("1", startRoom.getRoomNumber());
    assertEquals("Start Room", startRoom.getRoomName());

    // Check items loaded correctly
    assertTrue(startRoom.getItems().size() > 0);
    Item testItem = startRoom.getItem("Test Item");
    assertNotNull(testItem);
    assertEquals(10, testItem.getValue());
  }

  @Test
  @DisplayName("Should handle player navigation between rooms")
  void testPlayerNavigation() {
    Player player = gameWorld.getPlayer();

    // Start in room 1
    assertEquals("1", player.getCurrentRoom().getRoomNumber());

    // Check available exits
    assertTrue(player.canMove(Direction.NORTH));
    assertFalse(player.canMove(Direction.SOUTH));
    assertFalse(player.canMove(Direction.EAST));
    assertFalse(player.canMove(Direction.WEST));

    // Move to room 2
    assertTrue(player.move(Direction.NORTH));
    assertEquals("2", player.getCurrentRoom().getRoomNumber());
    assertEquals("Puzzle Room", player.getCurrentRoom().getRoomName());

    // Check puzzle exists in room 2
    Puzzle puzzle = player.getCurrentRoom().getPuzzle();
    assertNotNull(puzzle);
    assertEquals("Door Puzzle", puzzle.getName());

    // Try to move north (should be blocked by puzzle)
    assertFalse(player.move(Direction.NORTH));
    assertEquals("2", player.getCurrentRoom().getRoomNumber());

    // Move back to room 1
    assertTrue(player.move(Direction.SOUTH));
    assertEquals("1", player.getCurrentRoom().getRoomNumber());
  }

  @Test
  @DisplayName("Should handle item interaction correctly")
  void testItemInteraction() {
    Player player = gameWorld.getPlayer();
    Room startRoom = player.getCurrentRoom();

    // Take the test item
    Item testItem = startRoom.getItem("Test Item");
    assertNotNull(testItem);

    assertTrue(player.addToInventory(testItem));
    startRoom.removeItem(testItem);

    // Check inventory
    assertEquals(1, player.getInventory().size());
    assertTrue(player.getInventory().contains(testItem));
    assertFalse(startRoom.getItems().contains(testItem));

    // Use the item
    assertEquals(1, testItem.getUsesRemaining());
    assertTrue(testItem.use());
    assertEquals(0, testItem.getUsesRemaining());

    // Try to use it again (should fail)
    assertFalse(testItem.use());

    // Drop the item
    assertTrue(player.removeFromInventory(testItem));
    startRoom.addItem(testItem);

    assertEquals(0, player.getInventory().size());
    assertFalse(player.getInventory().contains(testItem));
    assertTrue(startRoom.getItems().contains(testItem));
  }

  @Test
  @DisplayName("Should handle puzzle solving correctly")
  void testPuzzleSolving() {
    Player player = gameWorld.getPlayer();

    // Create and add puzzle key to inventory
    Item puzzleKey = new Item("Puzzle Key", 1, 1, 1, 20,
            "The key unlocks the door.", "A key that unlocks puzzles.");
    player.addToInventory(puzzleKey);

    // Move to room 2 with the puzzle
    player.move(Direction.NORTH);
    assertEquals("2", player.getCurrentRoom().getRoomNumber());

    // Check that north exit is blocked
    assertEquals("-3", player.getCurrentRoom().getExitRoomNumber(Direction.NORTH));

    // Solve the puzzle
    boolean solved = gameWorld.applySolution("Puzzle Key");
    assertTrue(solved);

    // Check that the puzzle is now inactive
    Puzzle puzzle = player.getCurrentRoom().getPuzzle();
    assertFalse(puzzle.isActive());

    // Check that exit is now unblocked
    assertEquals("3", player.getCurrentRoom().getExitRoomNumber(Direction.NORTH));

    // Check player scored points
    assertEquals(50, player.getScore());

    // Move to the previously blocked room
    assertTrue(player.move(Direction.NORTH));
    assertEquals("3", player.getCurrentRoom().getRoomNumber());
  }

  @Test
  @DisplayName("Should handle monster encounters correctly")
  void testMonsterEncounter() {
    Player player = gameWorld.getPlayer();

    // Add the monster sword to inventory
    Item monsterSword = new Item("Monster Sword", 3, 3, 3, 30,
            "You swing the sword at the monster.", "A sword for fighting monsters.");
    player.addToInventory(monsterSword);

    // Move to room 2
    player.move(Direction.NORTH);

    // Solve the puzzle to unblock the path to room 3
    gameWorld.applySolution("Puzzle Key");

    // Move to room 3 with the monster
    player.move(Direction.NORTH);
    assertEquals("3", player.getCurrentRoom().getRoomNumber());

    // Check monster exists
    Monster monster = player.getCurrentRoom().getMonster();
    assertNotNull(monster);
    assertEquals("Test Monster", monster.getName());
    assertTrue(monster.isActive());

    // Simulate monster attacking player
    int initialHealth = player.getHealth();
    monster.attack(player);
    assertTrue(player.getHealth() < initialHealth);

    // Defeat the monster
    boolean defeated = gameWorld.applySolution("Monster Sword");
    assertTrue(defeated);
    assertFalse(monster.isActive());

    // Check player scored points
    assertEquals(75 + 50, player.getScore()); // Monster + puzzle points

    // Now we should be able to move to room 4
    assertTrue(player.move(Direction.NORTH));
    assertEquals("4", player.getCurrentRoom().getRoomNumber());
  }

  @Test
  @DisplayName("Should save and load game state correctly")
  void testSaveAndLoadGame() throws IOException {
    // Set up initial game state
    Player player = gameWorld.getPlayer();
    player.setName("TestPlayer");
    player.setScore(50);

    // Move to room 2
    player.move(Direction.NORTH);

    // Save the game
    String savePath = tempDir.resolve("game_save.json").toString();
    gameWorld.saveGame(savePath);

    // Create a new game world instance
    String gameFilePath = createTestGameFile();
    GameWorld newGameWorld = new GameWorld(gameFilePath);

    // Check initial state is different
    assertNotEquals("TestPlayer", newGameWorld.getPlayer().getName());
    assertNotEquals(50, newGameWorld.getPlayer().getScore());
    assertEquals("1", newGameWorld.getPlayer().getCurrentRoom().getRoomNumber());

    // Load the saved game
    newGameWorld.loadGame(savePath);

    // Check state was restored correctly
    assertEquals("TestPlayer", newGameWorld.getPlayer().getName());
    assertEquals(50, newGameWorld.getPlayer().getScore());
    assertEquals("2", newGameWorld.getPlayer().getCurrentRoom().getRoomNumber());
  }

  @Test
  @DisplayName("Should handle game loading with existing save file")
  void testLoadExistingSaveFile() throws IOException {
    // Create a save file with pre-defined state
    String savePath = createTestSaveFile();

    // Load the save file
    gameWorld.loadGame(savePath);

    // Verify the loaded state
    Player player = gameWorld.getPlayer();
    assertEquals("TestPlayer", player.getName());
    assertEquals(90, player.getHealth());
    assertEquals(100, player.getScore());
    assertEquals("3", player.getCurrentRoom().getRoomNumber());

    // Check inventory contains monster sword
    assertEquals(1, player.getInventory().size());
    Item sword = player.getItemFromInventory("Monster Sword");
    assertNotNull(sword);
    assertEquals(3, sword.getUsesRemaining());

    // Verify puzzle in room 2 is solved
    Room puzzleRoom = gameWorld.getRoom("2");
    Puzzle puzzle = puzzleRoom.getPuzzle();
    assertFalse(puzzle.isActive());

    // Verify monster in room 3 is still active
    Room monsterRoom = gameWorld.getRoom("3");
    Monster monster = monsterRoom.getMonster();
    assertTrue(monster.isActive());
  }

  @Test
  @DisplayName("Should handle player health and damage correctly")
  void testPlayerHealthAndDamage() {
    Player player = gameWorld.getPlayer();
    assertEquals(100, player.getHealth());
    assertEquals("AWAKE", player.getHealthStatus());

    // Take damage
    player.takeDamage(30);
    assertEquals(70, player.getHealth());
    assertEquals("FATIGUED", player.getHealthStatus());

    // Take more damage
    player.takeDamage(40);
    assertEquals(30, player.getHealth());
    assertEquals("WOOZY", player.getHealthStatus());

    // Take fatal damage
    player.takeDamage(50);
    assertEquals(0, player.getHealth());
    assertEquals("ASLEEP", player.getHealthStatus());
  }
}