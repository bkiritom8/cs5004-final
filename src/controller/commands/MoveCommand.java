package controller.commands;

import java.io.IOException;

import controller.Command;
import controller.GameController;
import model.Direction;
import model.GameWorld;

/**
 * Command for player movement in a direction.
 */
public class MoveCommand implements Command {
  private GameController controller;
  private Direction direction;

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

  public MoveCommand(GameWorld ignoredTestWorld) {
  }

  @Override
  public void execute() {
    try {
      controller.move(direction);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}