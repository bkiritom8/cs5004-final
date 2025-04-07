package controller;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import model.GameWorld;
import model.Player;
import model.Room;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * TextControllerTest verifies the TextController behavior.
 */
public class TextControllerTest {

  /**
   * Creates a simple dummy GameWorld instance.
   *
   * @return a dummy GameWorld for testing.
   * @throws IOException if an I/O error occurs.
   */
  private GameWorld createDummyGameWorld() throws IOException {
    return new GameWorld("dummy.json") {
      private final Player player;

      {
        Room room = new Room("Dummy Room", "1", "A dummy room for testing.", new HashMap<>());
        room.setItems(new ArrayList<>());
        room.setFixtures(new ArrayList<>());
        room.setPuzzle(null);
        room.setMonster(null);
        player = new Player(room);
      }

      @Override
      public String getGameName() {
        return "Dummy Game";
      }

      @Override
      public Player getPlayer() {
        return player;
      }

      @Override
      public void setPlayerName(String name) {
        player.setName(name);
      }

      @Override
      public boolean applySolution(String solution) {
        return false;
      }

      @Override
      public void saveGame(String filename) {
      }

      @Override
      public void loadGame(String filename) {
      }
    };
  }


  /**
   * Test that TextController starts without throwing an exception.
   *
   * @throws IOException if an I/O error occurs during GameWorld creation.
   */
  @Test
  public void testTextControllerStart() throws IOException {
    GameWorld dummyWorld = createDummyGameWorld();
    TextController controller = new TextController(dummyWorld,
            new BufferedReader(new InputStreamReader(System.in)),
            System.out);
    assertDoesNotThrow(controller::start, "TextController started successfully");
  }

  /**
   * Test that TextController processes a valid command without error.
   *
   * <p>This test uses reflection to invoke the private processCommand(String) method
   * defined in GameController. It ensures that processing the 'look' command does not
   * throw any exceptions.</p>
   *
   * @throws Exception if reflection fails or an I/O error occurs.
   */
  @Test
  public void testProcessValidCommand() throws Exception {
    GameWorld dummyWorld = createDummyGameWorld();
    TextController controller = new TextController(dummyWorld, new BufferedReader(new InputStreamReader(System.in)), System.out);

    // Use reflection to access the private processCommand(String) method from GameController.
    Method processCommandMethod = TextController.class.getSuperclass().getDeclaredMethod("processCommand", String.class);
    processCommandMethod.setAccessible(true);

    // Invoke the private method with the command "look" and verify no exception is thrown.
    assertDoesNotThrow(() -> processCommandMethod.invoke(controller, "look"), "Processing 'look' command should not throw an exception");
  }

  @Test
  void start() {
  }
}
