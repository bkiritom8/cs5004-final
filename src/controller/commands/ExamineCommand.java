package controller.commands;

import controller.Command;
import controller.GameController;

/**
 * Command for examining an object.
 */
public class ExamineCommand implements Command {
  private final GameController controller;
  private final String target;

  /**
   * Creates a new examine command.
   *
   * @param controller The controller that will execute the command
   * @param target The name of the object to examine
   */
  public ExamineCommand(GameController controller, String target) {
    this.controller = controller;
    this.target = target;
  }

  @Override
  public void execute() {
    controller.examine(target);
  }
}