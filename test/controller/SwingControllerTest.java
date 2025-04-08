package controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;

import model.GameWorld;
import model.Player;
import model.Room;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * simple tests for the GUI controller.
 * checks that the basic commands work
 */
public class SwingControllerTest {
  private SwingController controller;

  @BeforeEach
  public void setUp() {
    // make a simple test world with one room
    GameWorld world;
    try {
      world = makeTestWorld();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    controller = new SwingController(world);
    // let gui start up
    sleep(200);
  }

  // helper to make a test world
  private GameWorld makeTestWorld() throws IOException {
    // just one room with no exits
    Room room = new Room("Test Room", "1", "a simple test room.", new HashMap<>());
    room.setItems(new ArrayList<>());
    room.setFixtures(new ArrayList<>());

    Player player = new Player(room);

    return new MockGameWorld(player);
  }

  // helper to pause briefly
  private void sleep(int ms) {
    try {
      Thread.sleep(ms);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  // helper to get the output text
  private String getOutput() throws Exception {
    Field field = SwingController.class.getDeclaredField("outputArea");
    field.setAccessible(true);
    JTextArea area = (JTextArea) field.get(controller);
    return area.getText().toLowerCase();
  }

  // helper to send a command
  private void sendCommand(String cmd) throws Exception {
    // get the input field
    Field field = SwingController.class.getDeclaredField("inputField");
    field.setAccessible(true);
    JTextField input = (JTextField) field.get(controller);

    // send the command
    input.setText(cmd);
    input.getActionListeners()[0].actionPerformed(
            new ActionEvent(input, ActionEvent.ACTION_PERFORMED, null)
    );

    // wait a moment
    sleep(100);
  }

  // test welcome message appears
  @Test
  public void testWelcome() throws Exception {
    String out = getOutput();
    assertTrue(out.contains("welcome"), "should show welcome");
    assertTrue(out.contains("test room"), "should show room name");
  }

  // test help command
  @Test
  public void testHelp() throws Exception {
    sendCommand("help");
    String out = getOutput();
    assertTrue(out.contains("commands"), "should list commands");
    assertTrue(out.contains("north"), "should show movement commands");
  }

  // test look command
  @Test
  public void testLook() throws Exception {
    sendCommand("look");
    String out = getOutput();
    assertTrue(out.contains("test room"), "should show room name");
    assertTrue(out.contains("simple test room"), "should show description");
  }

  // test inventory command
  @Test
  public void testInventory() throws Exception {
    sendCommand("i");
    String out = getOutput();
    assertTrue(out.contains("inventory is empty"), "should show empty inventory");
  }

  // test bad command
  @Test
  public void testBadCommand() throws Exception {
    sendCommand("xyz");
    String out = getOutput();
    assertTrue(out.contains("unknown command"), "should show error");
  }

  // test move in invalid direction
  @Test
  public void testBadMove() throws Exception {
    sendCommand("north");
    String out = getOutput();
    assertTrue(out.contains("can't go"), "should show can't move message");
  }

  // minimal mock class to bypass constructor requirement
  private static class MockGameWorld extends GameWorld {
    private final Player player;

    public MockGameWorld(Player player) throws IOException {
      super("dummy.json"); // doesn't actually load since we override methods
      this.player = player;
    }

    @Override
    public String getGameName() {
      return "Test Game";
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
  }
}
