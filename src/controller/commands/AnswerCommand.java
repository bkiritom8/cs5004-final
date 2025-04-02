package controller.commands;

import controller.Command;
import controller.GameController;

/**
 * Command for answering a puzzle.
 */
public class AnswerCommand implements Command {
  private final GameController controller;
  private final String answer;

  /**
   * Creates a new answer command.
   *
   * @param controller The controller that will execute the command
   * @param answer The answer to the puzzle
   */
  public AnswerCommand(GameController controller, String answer) {
    this.controller = controller;
    this.answer = answer;
  }

  @Override
  public void execute() {
    controller.answerPuzzle(answer);
  }
}