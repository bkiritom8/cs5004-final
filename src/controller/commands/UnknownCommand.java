package controller.commands;

import controller.Command;
import controller.GameController;

/**
 * Command for handling unknown or invalid input.
 */
public class UnknownCommand implements Command {
  private final GameController controller;
  private final String commandString;

  /**
   * Creates a new unknown command.
   *
   * @param controller The controller that will execute the command
   * @param commandString The original command string
   */
  public UnknownCommand(GameController controller, String commandString) {
    this.controller = controller;
    this.commandString = commandString;
  }

  @Override
  public void execute() {
    controller.view.displayMessage("I don't understand that command: " + commandString);
  }
}