package view;

/**
 * ConsoleView displays game info to the console or captures it in batch mode.
 */
public class ConsoleView implements GameView {

  private final boolean isBatchMode;
  private final StringBuilder output;

  /**
   * Constructs a ConsoleView.
   *
   * @param isBatchMode true for batch mode, false for live console output
   */
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

  /**
   * Returns output collected in batch mode.
   *
   * @return the output string
   */
  public String getBatchOutput() {
    if (!isBatchMode) {
      throw new IllegalStateException("Not in batch mode");
    }
    return output.toString();
  }
}

