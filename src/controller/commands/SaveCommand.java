package controller.commands;

import java.io.IOException;

import controller.Command;
import controller.GameController;

/**
 * Command for saving the game.
 */
public class SaveCommand implements Command {
  private final GameController controller;

  /**
   * Creates a new save command.
   *
   * @param controller The controller that will execute the command
   */
  public SaveCommand(GameController controller) {
    this.controller = controller;
  }

  @Override
  public void execute() throws IOException {
    controller.saveGame();
  }
}