package controller.commands;

import controller.Command;
import controller.GameController;

/**
 * Command for quitting the game.
 */
public class QuitCommand implements Command {
  private final GameController controller;

  /**
   * Creates a new quit command.
   *
   * @param controller The controller that will execute the command
   */
  public QuitCommand(GameController controller) {
    this.controller = controller;
  }

  @Override
  public void execute() {
    controller.quitGame();
  }
}