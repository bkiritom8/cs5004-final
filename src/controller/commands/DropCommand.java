package controller.commands;

import java.io.IOException;

import controller.Command;
import controller.GameController;

/**
 * Command for dropping an item.
 */
public class DropCommand implements Command {
  private final GameController controller;
  private final String itemName;

  /**
   * Creates a new drop command.
   *
   * @param controller The controller that will execute the command
   * @param itemName The name of the item to drop
   */
  public DropCommand(GameController controller, String itemName) {
    this.controller = controller;
    this.itemName = itemName;
  }

  @Override
  public void execute() throws IOException {
    controller.dropItem(itemName);
  }
}