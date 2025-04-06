package view.text;

import java.io.StringWriter;
import view.GameView;

/**
 * Text-based implementation of the GameView interface.
 * Displays game information to the console with consistent formatting and supports both direct console output and batch mode.
 */
public class ConsoleView implements GameView {
  private StringBuilder outputBuffer;
  private boolean batchMode;

  /**
   * Constructs a ConsoleView.
   * If batchMode is true, output is built as a string in an internal buffer.
   * Otherwise, output is printed directly to the console.
   *
   * @param batchMode true for batch mode (string building), false for direct console output
   */
  public ConsoleView(boolean batchMode) {
    this.batchMode = batchMode;
    if (batchMode) {
      outputBuffer = new StringBuilder();
    }
  }

  /**
   * Constructs a ConsoleView using the provided StringWriter.
   * This constructor initializes the view in batch mode with the given StringWriter.
   *
   * @param output a StringWriter to which the output will be written in batch mode
   */
  public ConsoleView(StringWriter output) {
    // Initialize batch mode and synchronize with the provided StringWriter's content.
    this.batchMode = true;
    outputBuffer = new StringBuilder();
    outputBuffer.append(output.toString());
  }

  /**
   * Displays a general message.
   * This method delegates to the internal display method to output the message with consistent formatting.
   *
   * @param message the message to display
   */
  @Override
  public void displayMessage(String message) {
    display(message);
  }

  /**
   * Displays a description of the room.
   * This method delegates to the internal display method to output the room description.
   *
   * @param roomDescription the description of the room
   */
  @Override
  public void displayRoom(String roomDescription) {
    display(roomDescription);
  }

  /**
   * Displays the game over message.
   * This method outputs a standardized "Game Over" message.
   */
  @Override
  public void displayGameOver() {
    display("Game Over");
  }

  /**
   * Displays a message by formatting it consistently.
   * In batch mode, the message is appended to the output buffer.
   * Otherwise, the message is printed directly to the console.
   *
   * @param message the message to display
   */
  public void display(String message) {
    String formattedMessage = formatMessage(message);
    if (batchMode) {
      outputBuffer.append(formattedMessage).append("\n");
    } else {
      System.out.println(formattedMessage);
    }
  }

  /**
   * Shows a message.
   * This method is a synonym for displayMessage.
   *
   * @param s the message to show
   */
  @Override
  public void showMessage(String s) {
    displayMessage(s);
  }

  /**
   * Formats a message with consistent formatting (e.g., adding a header).
   *
   * @param message the raw message to format
   * @return the formatted message
   */
  private String formatMessage(String message) {
    return "[Game Info] " + message;
  }

  /**
   * Returns the complete output string built in batch mode.
   * If not in batch mode, this method returns an empty string.
   *
   * @return the output string accumulated in the internal buffer, or an empty string if not in batch mode
   */
  public String getBatchOutput() {
    if (batchMode && outputBuffer != null) {
      return outputBuffer.toString();
    }
    return "";
  }
}
