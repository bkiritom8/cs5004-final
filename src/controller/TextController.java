package controller;

import java.io.IOException;

import model.GameWorld;

/**
 * TestController is a dummy controller used for testing.
 * It simply prints a test message and basic game world information.
 */
public class TestController extends GameController {
  private final GameWorld gameWorld;

  /**
   * Constructs a TestController with the given GameWorld.
   *
   * @param gameWorld the game world model
   */
  public TestController(GameWorld gameWorld) {
    this.gameWorld = gameWorld;
  }

  /**
   * Starts the test controller by printing a test message.
   */
  @Override
  public void start() throws IOException {
    System.out.println("Test Controller Started");
    System.out.println("Game World: " + gameWorld.getGameName());
    System.out.println("Current Room: " + gameWorld.getPlayer().getCurrentRoom().getName());
  }
}