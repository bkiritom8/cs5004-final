package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import model.GameWorld;

/**
 * TextController handles text-based interaction with the game.
 * It displays the welcome screen, processes player commands,
 * and provides a complete interactive experience.
 */
public class TextController extends GameController {
  private final BufferedReader bufferedReader;
  private final PrintStream out;
  private boolean running;

  /**
   * Constructs a TextController with the given GameWorld.
   *
   * @param gameWorld      the game world model
   * @param bufferedReader input reader
   * @param out            output stream
   */
  public TextController(GameWorld gameWorld, BufferedReader bufferedReader, PrintStream out) {
    super(gameWorld);
    this.bufferedReader = bufferedReader;
    this.out = out;
    this.running = false;
  }

  /**
   * Starts the text controller, displaying the welcome screen
   * and entering the main game loop.
   */
  @Override
  public void start() throws IOException {
    running = true;

    // Display welcome screen
    displayWelcome();

    // Get player name
    promptForPlayerName();

    // Show initial room description
    lookAround();

    // Enter main game loop
    while (running && bufferedReader.ready()) {
      try {
        // Show command prompt
        displayMenu();

        // Get and process player command
        String command = bufferedReader.readLine();
        if (command != null) {
          processCommand(command.trim().toLowerCase());
        }

        // Check if player's health is depleted
        if (gameWorld.getPlayer().getHealth() <= 0) {
          displayGameOver();
          endGame();
        }
      } catch (IOException e) {
        out.println("Error reading input: " + e.getMessage());
        endGame();
      }
    }
  }

  /**
   * Displays the welcome message for the game.
   */
  public void displayWelcome() {
    out.println("==================================");
    out.println("Welcome to " + gameWorld.getGameName() + "!");
    out.println("An adventure awaits you...");
    out.println("==================================\n");
  }

  /**
   * Prompts the player to enter their name.
   */
  public void promptForPlayerName() {
    try {
      out.print("Enter your name: ");
      out.flush();
      String name = bufferedReader.readLine();
      if (name != null && !name.trim().isEmpty()) {
        gameWorld.setPlayerName(name.trim());
        out.println("Welcome, " + name.trim() + "! Your adventure begins now.\n");
      } else {
        gameWorld.setPlayerName("Adventurer");
        out.println("Welcome, Adventurer! Your journey begins now.\n");
      }
    } catch (IOException e) {
      out.println("Error reading name: " + e.getMessage());
      gameWorld.setPlayerName("Adventurer");
    }
  }

  /**
   * Displays the current room and its contents.
   */
  private void lookAround() {
    try {
      look();
    } catch (IOException e) {
      out.println("Error displaying room: " + e.getMessage());
    }
  }

  /**
   * Displays the available commands to the player.
   */
  public void displayMenu() {
    out.println("\nAvailable commands:");
    out.println("  (n)orth, (s)outh, (e)ast, (w)est - Move in a direction");
    out.println("  (l)ook - Look around the room");
    out.println("  (i)nventory - Check your inventory");
    out.println("  (t)ake [item] - Pick up an item");
    out.println("  (d)rop [item] - Drop an item");
    out.println("  e(x)amine [target] - Examine something");
    out.println("  (u)se [item] - Use an item");
    out.println("  (a)nswer [text] - Answer a riddle or puzzle");
    out.println("  attac(k) - Attack a monster");
    out.println("  sa(v)e - Save the game");
    out.println("  (r)estore - Restore a saved game");
    out.println("  (q)uit - Quit the game");
    out.print("\nWhat would you like to do? ");
    out.flush();
  }

  /**
   * Displays the game over message.
   */
  public void displayGameOver() {
    out.println("\n===== GAME OVER =====");
    out.println("Your health has been depleted.");
    out.println("Final score: " + gameWorld.getPlayer().getScore());
    out.println("=====================\n");
  }

  /**
   * Ends the game loop.
   */
  public void endGame() {
    running = false;
  }

  /**
   * Gets the output stream.
   *
   * @return the output stream
   */
  public PrintStream getOut() {
    return out;
  }

  /**
   * Gets the buffered reader.
   *
   * @return the buffered reader
   */
  public BufferedReader getBufferedReader() {
    return bufferedReader;
  }
}