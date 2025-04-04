package view.text;

import java.io.IOException;
import view.GameView;

/**
 * ConsoleView is a text-based implementation of GameView for console and batch output.
 */
public class ConsoleView implements GameView {
  private final Appendable output;

  /**
   * Constructs a ConsoleView that writes to the given Appendable.
   *
   * @param output The output target (e.g., System.out, StringBuilder)
   */
  public ConsoleView(Appendable output) {
    this.output = output;
  }

  /**
   * Displays a generic message to the output.
   *
   * @param message The message to display
   */
  @Override
  public void displayMessage(String message) {
    try {
      output.append(message).append("\n");
    } catch (IOException e) {
      throw new RuntimeException("Error writing to output", e);
    }
  }

  /**
   * Displays the current room description.
   *
   * @param roomDescription The room's description text
   */
  @Override
  public void displayRoom(String roomDescription) {
    displayMessage("== Room ==");
    displayMessage(roomDescription);
  }

  /**
   * Displays the player's current inventory.
   *
   * @param inventory The inventory description
   */
  @Override
  public void displayInventory(String inventory) {
    displayMessage("== Inventory ==");
    displayMessage(inventory);
  }

  /**
   * Displays the game over message.
   */
  @Override
  public void displayGameOver() {
    displayMessage("== GAME OVER ==");
  }

  /**
   * Displays a message (generic fallback).
   *
   * @param message The message to show
   */
  @Override
  public void display(String message) {
    // Unused, could be redirected to displayMessage if needed
  }

  /**
   * Shows a message (alias method, may overlap with displayMessage).
   *
   * @param s The string to show
   */
  @Override
  public void showMessage(String s) {
    // Unused, could be redirected to displayMessage if needed
  }

  /**
   * Gets user input (not supported in batch/console mode).
   *
   * @return Always returns an empty string
   */
  @Override
  public String getUserInput() {
    return "";
  }
}
