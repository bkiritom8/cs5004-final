package controller;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import model.GameWorld;
import model.Player;
import model.Room;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TextControllerTest verifies the behavior of the TextController.
 */
public class TextControllerTest {

  /**
   * Creates a dummy GameWorld without loading from a file.
   *
   * @return a stubbed GameWorld instance for testing
   */
  private GameWorld createDummyWorld() {
    return new GameWorld() {
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
        return "Adventure Game";
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
   * Test that TextController starts and outputs welcome and prompt text without errors.
   *
   * @throws IOException if setup fails
   */
  @Test
  public void testTextControllerStart() throws IOException {
    String input = "Tester\nquit\n";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);

    GameWorld dummyWorld = createDummyWorld();
    TextController controller = new TextController(dummyWorld,
            new BufferedReader(new InputStreamReader(inputStream)),
            printStream);

    controller.start();
    String output = outputStream.toString().toLowerCase();

    assertTrue(output.contains("welcome to adventure game"));
    assertTrue(output.contains("welcome, tester"));
    assertTrue(output.contains("what would you like to do"));
  }

  /**
   * Test processing a valid command: look.
   *
   * @throws IOException if setup fails
   */
  @Test
  public void testProcessValidLookCommand() throws IOException {
    InputStream input = new ByteArrayInputStream("".getBytes());
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    PrintStream print = new PrintStream(out);

    GameWorld dummyWorld = createDummyWorld();
    TextController controller = new TextController(dummyWorld,
            new BufferedReader(new InputStreamReader(input)), print);

    controller.processCommand("look");

    String output = out.toString().toLowerCase();
    assertTrue(output.contains("dummy room"));
  }

  /**
   * Test that an unknown command displays a help suggestion message.
   *
   * @throws IOException if setup fails
   */
  @Test
  public void testUnknownCommand() throws IOException {
    InputStream input = new ByteArrayInputStream("".getBytes());
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    PrintStream print = new PrintStream(out);

    GameWorld dummyWorld = createDummyWorld();
    TextController controller = new TextController(dummyWorld,
            new BufferedReader(new InputStreamReader(input)), print);

    controller.processCommand("fly");

    String output = out.toString().toLowerCase();
    assertTrue(output.contains("i don't understand"));
  }

  /**
   * Test inventory display with an empty inventory.
   *
   * @throws IOException if setup fails
   */
  @Test
  public void testInventoryCommand() throws IOException {
    InputStream input = new ByteArrayInputStream("".getBytes());
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    PrintStream print = new PrintStream(out);

    GameWorld dummyWorld = createDummyWorld();
    TextController controller = new TextController(dummyWorld,
            new BufferedReader(new InputStreamReader(input)), print);

    controller.processCommand("inventory");

    String output = out.toString().toLowerCase();
    assertTrue(output.contains("your inventory is empty"));
  }
  /**
   * Test the 'take' command with a valid item in the room.
   */
  @Test
  public void testTakeItemCommand() throws IOException {
    GameWorld world = createDummyWorld();
    Room room = world.getPlayer().getCurrentRoom();

    room.addItem(new model.Item("key", 1, 1, 1, 0, "Use it", "A small key"));

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    TextController controller = new TextController(world,
            new BufferedReader(new StringReader("")), new PrintStream(out));

    controller.processCommand("take key");
    String output = out.toString().toLowerCase();

    assertTrue(output.contains("you pick up the key"));
  }

  /**
   * Test the 'drop' command with an item in the player's inventory.
   */
  @Test
  public void testDropItemCommand() throws IOException {
    GameWorld world = createDummyWorld();
    Player player = world.getPlayer();
    Room room = player.getCurrentRoom();

    model.Item item = new model.Item("gem", 1, 1, 1, 0, "Glows", "A glowing gem");
    player.addToInventory(item);

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    TextController controller = new TextController(world,
            new BufferedReader(new StringReader("")), new PrintStream(out));

    controller.processCommand("drop gem");
    String output = out.toString().toLowerCase();

    assertTrue(output.contains("you drop the gem"));
    assertNotNull(room.getItem("gem"));
  }

  /**
   * Test 'use' command with an item that has 0 uses remaining.
   */
  @Test
  public void testUseItemWithNoUsesLeft() throws IOException {
    GameWorld world = createDummyWorld();
    Player player = world.getPlayer();

    model.Item item = new model.Item("torch", 1, 1, 0, 0, "Flickers", "A burnt-out torch");
    player.addToInventory(item);

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    TextController controller = new TextController(world,
            new BufferedReader(new StringReader("")), new PrintStream(out));

    controller.processCommand("use torch");
    String output = out.toString().toLowerCase();

    assertTrue(output.contains("has no uses left"));
  }

  /**
   * Test 'answer' command when there is no puzzle in the room.
   */
  @Test
  public void testAnswerWithNoPuzzle() throws IOException {
    GameWorld world = createDummyWorld();

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    TextController controller = new TextController(world,
            new BufferedReader(new StringReader("")), new PrintStream(out));

    controller.processCommand("answer solution");
    String output = out.toString().toLowerCase();

    assertTrue(output.contains("no active puzzle"));
  }
}