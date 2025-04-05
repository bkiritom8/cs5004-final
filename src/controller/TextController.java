package controller;

import java.io.IOException;
import model.GameWorld;

/**
 * TextController is a dummy controller used for texting.
 * It simply prints a text message and basic game world information.
 */
public class TextController extends GameController {
  private final GameWorld gameWorld;

  /**
   * Constructs a TextController with the given GameWorld.
   *
   * @param gameWorld the game world model
   */
  public TextController(GameWorld gameWorld) {
    this.gameWorld = gameWorld;
  }

  /**
   * Starts the text controller by printing a text message.
   */
  public void start() throws IOException {
    System.out.println("Text Controller Started");
    System.out.println("Game World: " + gameWorld.getGameName());
    System.out.println("Current Room: " + gameWorld.getPlayer().getCurrentRoom().getName());
  }
}
