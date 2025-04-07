package view;

/**
 * Interface representing a view for the game. Supports output and user input.
 */
public interface GameView {

  /**
   * Displays a general message to the player.
   *
   * @param message The message to display
   */
  void displayMessage(String message);

  /**
   * Displays the current room description.
   *
   * @param roomDescription The description of the room
   */
  void displayRoom(String roomDescription);

  /**
   * Displays the game over message.
   */
  void displayGameOver();

  /**
   * Displays a generic message (alternative to displayMessage).
   *
   * @param message The message to show
   */
  void display(String message);

  /**
   * Displays a message using another format or method.
   *
   * @param s The string to show
   */
  void showMessage(String s);
}
