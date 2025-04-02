package controller.commands;

import controller.Command;
import controller.GameController;

/**
 * Command for loading a saved game.
 */
public class LoadCommand implements Command {
  private final GameController controller;

  /**
   * Creates a new load command.
   *
   * @param controller The controller that will execute the command
   */
  public LoadCommand(GameController controller) {
    this.controller = controller;
  }

  @Override
  public void execute() {
    controller.loadGame();
  }
}