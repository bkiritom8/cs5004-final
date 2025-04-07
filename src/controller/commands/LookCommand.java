package controller.commands;

import java.io.IOException;

import controller.Command;
import controller.GameController;

/**
 * Command for looking around the current room.
 */
public class LookCommand implements Command {
  private final GameController controller;

  /**
   * Creates a new look command.
   *
   * @param controller The controller that will execute the command
   */
  public LookCommand(GameController controller) {
    this.controller = controller;
  }

  @Override
  public void execute() throws IOException {
    controller.look();
  }
}