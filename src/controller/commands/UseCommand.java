package controller.commands;

import controller.Command;
import controller.GameController;

/**
 * Command for using an item.
 */
public class UseCommand implements Command {
  private final GameController controller;
  private final String itemName;

  /**
   * Creates a new use command.
   *
   * @param controller The controller that will execute the command
   * @param itemName The name of the item to use
   */
  public UseCommand(GameController controller, String itemName) {
    this.controller = controller;
    this.itemName = itemName;
  }

  @Override
  public void execute() {
    controller.useItem(itemName);
  }
}