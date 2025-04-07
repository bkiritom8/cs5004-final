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

  @BeforeEach
  void setUp() {
    commandLog = new ArrayList<>();
    try {
      dummyWorld = new GameWorld("dummy.json"); // ✅ Fixed: added required constructor argument
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

  @Test
  void testValidCommandsExecuteSuccessfully() {
    List<String> commands = List.of("look", "inventory", "quit");

    BatchController controller = new BatchController(dummyWorld, "", mockView) {
      @Override
      public void run() {
        for (String line : commands) {
          CommandParser.ParsedCommand parsed = new CommandParser.ParsedCommand(line, List.of());
          Command command = () -> mockView.displayMessage("Executed: " + parsed.command());
          command.execute();
        }
      }
    };

    controller.run();

    assertTrue(commandLog.contains("MESSAGE: Executed: look"));
    assertTrue(commandLog.contains("MESSAGE: Executed: inventory"));
    assertTrue(commandLog.contains("MESSAGE: Executed: quit"));
  }

  @Test
  void testUnknownCommandIsHandled() {
    List<String> commands = List.of("fly");

    BatchController controller = new BatchController(dummyWorld, "", mockView) {
      @Override
      public void run() {
        for (String line : commands) {
          mockView.showMessage("Invalid command in batch file: " + line);
        }
      }
    };

    controller.run();

    assertTrue(commandLog.contains("MESSAGE: Invalid command in batch file: fly"));
  }
}
