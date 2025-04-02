package controller.commands;

import controller.Command;
import controller.GameController;
import model.Direction;

/**
 * Command for player movement in a direction.
 */
public class MoveCommand implements Command {
  private final GameController controller;
  private final Direction direction;

  /**
   * Creates a new move command.
   *
   * @param controller The controller that will execute the command
   * @param direction The direction to move
   */
  public MoveCommand(GameController controller, Direction direction) {
    this.controller = controller;
    this.direction = direction;
  }

  @Override
  public void execute() {
    controller.move(direction);
  }
}