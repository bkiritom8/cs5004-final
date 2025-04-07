package view.text;

import java.io.StringWriter;
import view.GameView;

/**
 * Text-based implementation of the GameView interface.
 * Displays game information to the console with consistent formatting
 * and supports both direct console output and batch mode.
 */
public class ConsoleView implements GameView {
  private StringBuilder outputBuffer;
  private final boolean batchMode;

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
    this.batchMode = true;
    outputBuffer = new StringBuilder();
    outputBuffer.append(output.toString());
  }

  @Override
  public void displayMessage(String message) {
    display(message);
  }

  @Override
  public void displayRoom(String roomDescription) {
    display(roomDescription);
  }

  @Override
  public void displayInventory(String inventoryDescription) {
    display(inventoryDescription);
  }

  @Override
  public void displayGameOver() {
    display("Game Over");
  }

  @Override
  public void showMessage(String s) {
    displayMessage(s);
  }

  /**
   * Displays the given message, either to the console or to the output buffer,
   * depending on whether batch mode is enabled.
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


  private String formatMessage(String message) {
    return "[Game Info] " + message;
  }

  // Removed unused getBatchOutput() method
}
