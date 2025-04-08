package controller;

import java.io.BufferedReader;
import java.io.PrintStream;

import model.GameWorld;

/**
 * TextController is a dummy controller used for texting.
 * It simply prints a text message and basic game world information.
 */
public class TextController extends GameController {
  private final BufferedReader bufferedReader;
  private final PrintStream out;

  /**
   * Constructs a TextController with the given GameWorld.
   *
   * @param gameWorld      the game world model
   * @param bufferedReader the buffered reader for input
   * @param out            the print stream for output
   */
  public TextController(GameWorld gameWorld, BufferedReader bufferedReader, PrintStream out) {
    super(gameWorld, bufferedReader, out);
    this.bufferedReader = bufferedReader;
    this.out = out;
  }

  /**
   * Starts the text controller by printing a text message.
   */
  @Override
  public void start() {
    System.out.println("Text Controller Started");
    System.out.println("Game World: " + gameWorld.getGameName());
    System.out.println("Current Room: " + gameWorld.getPlayer().getCurrentRoom().getName());
  }

  public PrintStream getOut() {
    return out;
  }

  public BufferedReader getBufferedReader() {
    return bufferedReader;
  }
}