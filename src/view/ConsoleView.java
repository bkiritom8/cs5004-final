package view.text;

import view.GameView;

/**
 * ConsoleView displays game messages in either live or batch mode.
 */
public class ConsoleView implements GameView {

  private final boolean isBatchMode;
  private final StringBuilder output;

  public ConsoleView(boolean isBatchMode) {
    this.isBatchMode = isBatchMode;
    this.output = isBatchMode ? new StringBuilder() : null;
  }

  @Override
  public void display(String message) {
    if (isBatchMode) {
      output.append(message).append(System.lineSeparator());
    } else {
      System.out.println(message);
    }
  }

  public String getBatchOutput() {
    if (!isBatchMode) {
      throw new IllegalStateException("Not in batch mode");
    }
    return output.toString();
  }
}

