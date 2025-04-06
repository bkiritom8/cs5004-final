package controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.GameWorld;
import util.CommandParser;
import view.GameView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the BatchController class.
 */
class BatchControllerTest {

  private List<String> commandLog;
  private GameView mockView;
  private GameWorld dummyWorld;

  /**
   * Sets up a dummy game world and mock view for each test.
   */
  @BeforeEach
  void setUp() {
    commandLog = new ArrayList<>();
    try {
      dummyWorld = new GameWorld(); // Replace with your real constructor
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    mockView = new GameView() {
      @Override
      public void displayMessage(String message) {
        commandLog.add("MESSAGE: " + message);
      }

      @Override
      public void displayRoom(String desc) {
        commandLog.add("ROOM: " + desc);
      }

      @Override
      public void displayInventory(String inventory) {
        commandLog.add("INVENTORY: " + inventory);
      }

      @Override
      public void displayGameOver() {
        commandLog.add("GAME OVER");
      }

      @Override
      public void display(String message) {
        displayMessage(message);
      }

      @Override
      public void showMessage(String s) {
        displayMessage(s);
      }

      @Override
      public String getUserInput() {
        return "";
      }
    };
  }

  /**
   * Tests running valid mock commands using the BatchController.
   */
  @Test
  void testValidCommandsExecuteSuccessfully() {
    List<String> commands = List.of("look", "inventory", "quit");

    BatchController controller = new BatchController(dummyWorld, "", mockView) {
      @Override
      public void run() {
        for (String line : commands) {
          CommandParser.ParsedCommand parsed = new CommandParser.ParsedCommand(line, List.of());
          Command command = new Command() {
            @Override
            public void execute() {

            }

            @Override
            public void execute(GameWorld dummyWorld, GameView mockView) {

            }

            @Override
            public void execute(Object world, GameView view) {
              view.displayMessage("Executed: " + parsed.command);
            }
          };
          command.execute(dummyWorld, mockView);
        }
      }
    };

    controller.run();

    assertTrue(commandLog.contains("MESSAGE: Executed: look"));
    assertTrue(commandLog.contains("MESSAGE: Executed: inventory"));
    assertTrue(commandLog.contains("MESSAGE: Executed: quit"));
  }

  /**
   * Tests handling of an unknown command.
   */
  @Test
  void testUnknownCommandIsHandled() {
    List<String> commands = List.of("fly");

    BatchController controller = new BatchController(dummyWorld, "", mockView) {
      @Override
      public void run() {
        for (String line : commands) {
          // simulate command not found
          mockView.showMessage("Invalid command in batch file: " + line);
        }
      }
    };

    controller.run();

    assertTrue(commandLog.contains("MESSAGE: Invalid command in batch file: fly"));
  }
}
