package controller;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import model.GameWorld;
import model.Player;
import model.Room;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * TestControllerTest verifies the TestController behavior.
 */
public class TestControllerTest {

  /**
   * Creates a simple dummy GameWorld instance.
   */
  private GameWorld createDummyGameWorld() {
    return new GameWorld() {
      private final Player player;
      private final Room room;

      {
        room = new Room("Dummy Room", "1", "A dummy room for testing.", new HashMap<>());
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
      public void saveGame(String filename) throws IOException {
      }

      @Override
      public void loadGame(String filename) throws IOException {
      }
    };
  }

  /**
   * Test that TestController starts without throwing an exception.
   */
  @Test
  public void testTestControllerStart() {
    GameWorld dummyWorld = createDummyGameWorld();
    TestController controller = new TestController(dummyWorld);
    assertDoesNotThrow(() -> controller.start(), "TestController started successfully");
  }
}