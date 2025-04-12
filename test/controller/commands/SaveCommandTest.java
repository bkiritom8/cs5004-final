package controller.commands;

import controller.Command;
import controller.GameController;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for the SaveCommand.
 */
public class SaveCommandTest {

  private GameWorld testWorld;
  private Player player;
  private GameController controller;

  @BeforeEach
  void setUp() throws IOException {
    // Step 1: Create a basic game world with one room and player
    Room room = new Room("Test Room", "1", "A room used for testing.", new HashMap<>());
    room.setExitRoomNumber(Direction.NORTH, "0");
    room.setItems(new ArrayList<>());
    room.setFixtures(new ArrayList<>());
    room.setPuzzle(null);
    room.setMonster(null);

    player = new Player(room);
    testWorld = new GameWorld("dummy.json") {
      @Override public String getGameName() { return "Test Game"; }
      @Override public Player getPlayer() { return player; }
      @Override public void setPlayerName(String name) { player.setName(name); }
      @Override public boolean applySolution(String s) { return true; }
    };
    GameController controller = new GameController(testWorld);

  }

  @Test
  @DisplayName("Should execute SaveCommand without error")
  void testCommandExecution() throws IOException {
    // Step 2: Instantiate the command
    Command command = new SaveCommand(controller);

    // Step 3: Run the command
    command.execute();

    // Step 4: (Generic test) just ensure it runs
    assertNotNull(testWorld.getPlayer());
  }
}
