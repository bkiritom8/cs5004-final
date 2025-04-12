package controller;

import model.GameWorld;
import model.Item;
import model.Player;
import model.Room;
import view.GameView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the BatchController using stubbed GameWorld and test commands.
 */
class BatchControllerTest {

  private List<String> outputLog;
  private GameView view;
  private GameWorld gameWorld;
  private Path tempDir;

  @BeforeEach
  void setUp() throws IOException {
    outputLog = new ArrayList<>();
    tempDir = Files.createTempDirectory("batch_test");

    gameWorld = new StubGameWorld(); // No file loading

    view = new GameView() {
      @Override
      public void displayMessage(String message) {
        outputLog.add("MESSAGE: " + message);
      }

      @Override
      public void displayRoom(String desc) {
        outputLog.add("ROOM: " + desc);
      }

      @Override
      public void displayInventory(String inventory) {
        outputLog.add("INVENTORY: " + inventory);
      }

      @Override
      public void displayGameOver() {
        outputLog.add("GAME OVER");
      }

      @Override
      public void display(String message) {
        displayMessage(message);
      }

      @Override
      public void showMessage(String s) {
        displayMessage(s);
      }
    };
  }

  /**
   * Helper method to create a temporary file with the given commands.
   */
  private String createCommandFile(List<String> commands) throws IOException {
    File file = new File(tempDir.toFile(), "commands.txt");
    try (FileWriter writer = new FileWriter(file)) {
      // Add a dummy player name as the first line
      writer.write("TestPlayer\n");

      // Write each command on a new line
      for (String command : commands) {
        writer.write(command + "\n");
      }
    }
    return file.getAbsolutePath();
  }

  @Test
  void testValidCommandsExecuteSuccessfully() throws IOException {
    List<String> commands = List.of("look", "inventory", "quit");
    String filePath = createCommandFile(commands);

    BatchController controller = new BatchController(gameWorld, filePath, view);
    controller.run();

    assertTrue(outputLog.stream().anyMatch(s -> s.contains("Executed: look") || s.contains("> look")),
            "Should execute look command");
    assertTrue(outputLog.stream().anyMatch(s -> s.contains("Executed: inventory") || s.contains("> inventory")),
            "Should execute inventory command");
    assertTrue(outputLog.stream().anyMatch(s -> s.contains("Executed: quit") || s.contains("> quit") || s.contains("Exiting game")),
            "Should execute quit command");
  }

  @Test
  void testUnknownCommandIsHandled() throws IOException {
    List<String> commands = List.of("fly");
    String filePath = createCommandFile(commands);

    BatchController controller = new BatchController(gameWorld, filePath, view);
    controller.run();

    assertTrue(outputLog.stream().anyMatch(s -> s.contains("Invalid command") || s.contains("Unknown command")),
            "Should handle unknown command");
  }

  /**
   * Stub implementation of GameWorld that avoids file I/O.
   */
  private static class StubGameWorld extends GameWorld {
    public StubGameWorld() {
      super(); // No-op constructor avoids file loading
    }

    @Override
    public String getGameName() {
      return "Test Game";
    }

    // Override any necessary methods that are called during testing
    @Override
    public Player getPlayer() {
      return new StubPlayer();
    }

    @Override
    public void setPlayerName(String name) {
      // No-op for testing
    }

    // Add any other required method overrides
  }

  /**
   * Stub implementation of Player to avoid NPEs during testing.
   */
  private static class StubPlayer extends Player {
    public StubPlayer() {
      super(null); // We'll override methods to avoid NPE
    }

    @Override
    public Room getCurrentRoom() {
      return new StubRoom();
    }

    @Override
    public List<Item> getInventory() {
      return new ArrayList<>();
    }

    @Override
    public int getHealth() {
      return 100;
    }

    @Override
    public String getHealthStatus() {
      return "GOOD";
    }

    @Override
    public int getScore() {
      return 0;
    }

    // Add other necessary method overrides
  }

  /**
   * Stub implementation of Room to avoid NPEs during testing.
   */
  private static class StubRoom extends Room {
    public StubRoom() {
      super("Test Room", "1", "A test room", new HashMap<>());
    }

    @Override
    public String getName() {
      return "Test Room";
    }

    @Override
    public String getDescription() {
      return "A test room for batch testing";
    }

    @Override
    public List<Item> getItems() {
      return new ArrayList<>();
    }

    // Add other necessary method overrides
  }

  /**
   * Test that an empty command list does not produce any output or exceptions.
   */
  @Test
  void testEmptyCommandListDoesNothing() throws IOException {
    List<String> commands = new ArrayList<>();
    String filePath = createCommandFile(commands);

    BatchController controller = new BatchController(gameWorld, filePath, view);
    controller.run();

    // Should only contain output from processing player name
    assertEquals(1, outputLog.size(), "Should only have player name output");
  }

  /**
   * Test that passing null for command file path is handled safely.
   */
  @Test
  void testNullCommandFileHandledSafely() {
    BatchController controller = new BatchController(gameWorld, null, view);
    controller.run();

    // Confirm it doesn't crash
    assertNotNull(outputLog);
  }

  /**
   * Test that the quit command halts execution when it's encountered.
   */
  @Test
  void testQuitCommandStopsExecution() throws IOException {
    List<String> commands = List.of("quit", "look");
    String filePath = createCommandFile(commands);

    BatchController controller = new BatchController(gameWorld, filePath, view);
    controller.run();

    // Ensure quit was processed
    assertTrue(outputLog.stream().anyMatch(s -> s.contains("quit") || s.contains("Exiting")),
            "Should process quit command");

    // Ensure look was not processed after quit
    assertFalse(outputLog.stream().anyMatch(s -> s.contains("> look")),
            "Should not process look command after quit");
  }

  /**
   * Test that output matches expected command order.
   */
  @Test
  void testCommandOutputOrder() throws IOException {
    List<String> commands = List.of("look", "inventory", "quit");
    String filePath = createCommandFile(commands);

    BatchController controller = new BatchController(gameWorld, filePath, view);
    controller.run();

    // Extract just the command parts from output
    List<String> commandsInOutput = outputLog.stream()
            .filter(s -> s.startsWith("MESSAGE: >") || s.startsWith("> "))
            .toList();

    // Verify commands were processed in the correct order
    // This is a simplified check - your actual output format may differ
    assertTrue(commandsInOutput.size() >= 3, "Should have at least 3 command outputs");

    int lookIndex = -1;
    int inventoryIndex = -1;
    int quitIndex = -1;

    for (int i = 0; i < commandsInOutput.size(); i++) {
      String cmd = commandsInOutput.get(i);
      if (cmd.contains("look")) lookIndex = i;
      if (cmd.contains("inventory")) inventoryIndex = i;
      if (cmd.contains("quit")) quitIndex = i;
    }

    assertTrue(lookIndex < inventoryIndex, "look should come before inventory");
    assertTrue(inventoryIndex < quitIndex, "inventory should come before quit");
  }
}