package view.text;

import java.io.PrintWriter;
import java.io.StringWriter;
import view.GameView;

/**
 * Text-based implementation of the GameView interface.
 * Displays game information to the console or to a writer depending on mode.
 */
public class ConsoleView implements GameView {
  private final boolean batchMode;
  private final PrintWriter writer;

  /**
   * Constructs a ConsoleView.
   * If batchMode is true, output is built as a string in an internal buffer.
   * Otherwise, output is printed directly to the console.
   *
   * @param batchMode true for batch mode (string building), false for direct console output
   */
  public ConsoleView(boolean batchMode) {
    this.batchMode = batchMode;
    this.writer =
            batchMode ? new PrintWriter(new StringWriter()) : new PrintWriter(System.out, true);
  }

  /**
   * Constructs a ConsoleView using the provided StringWriter.
   * This constructor initializes the view in batch mode with the given StringWriter.
   *
   * @param output a StringWriter to which the output will be written in batch mode
   */
  public ConsoleView(StringWriter output) {
    this.batchMode = true;
    this.writer = new PrintWriter(output, true);
  }

  @Override
  public void displayMessage(String message) {
    display(message);
  }

  @Override
  public void displayRoom(String roomDescription) {
    display("== Room ==");
    display(roomDescription);
  }

  @Override
  public void displayInventory(String inventoryDescription) {
    display(inventoryDescription);
  }

  @Override
  public void displayGameOver() {
    display("GAME OVER");
  }

  @Override
  public void showMessage(String s) {
    displayMessage(s);
  }

  /**
   * Displays the given message to the appropriate writer or console.
   *
   * @param message the message to display
   */
  public void display(String message) {
    writer.println(message);
    writer.flush();
  }

  public boolean isBatchMode() {
    return batchMode;
  }
}
