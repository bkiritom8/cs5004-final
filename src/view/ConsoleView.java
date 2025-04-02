/* ConsoleView.java */
package view;

import view.GameView;

public class ConsoleView implements GameView {
  private boolean batchMode;
  private StringBuilder batchOutput;

  /**
   * Constructs a ConsoleView.
   *
   * @param batchMode If true, output will be built in a string for batch mode; otherwise, it prints directly to the console.
   */
  public ConsoleView(boolean batchMode) {
    this.batchMode = batchMode;
    if (batchMode) {
      batchOutput = new StringBuilder();
    }
  }

  /**
   * Displays a message. In batch mode, the message is appended to the output string;
   * otherwise, it is printed to the console.
   *
   * @param message The message to display.
   */
  @Override
  public void display(String message) {
    if (batchMode) {
      batchOutput.append(message).append(System.lineSeparator());
    } else {
      System.out.println(message);
    }
  }

  /**
   * Retrieves the built output string in batch mode.
   *
   * @return The batch output string, or an empty string if not in batch mode.
   */
  public String getBatchOutput() {
    if (batchMode) {
      return batchOutput.toString();
    }
    return "";
  }
}
