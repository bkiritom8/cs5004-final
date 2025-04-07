package controller.commands;

import java.io.IOException;

import controller.Command;
import controller.GameController;

/**
 * Command for taking an item.
 */
public class TakeCommand implements Command {
  private final GameController controller;
  private final String itemName;

  /**
   * Creates a new take command.
   *
   * @param controller The controller that will execute the command
   * @param itemName The name of the item to take
   */
  public TakeCommand(GameController controller, String itemName) {
    this.controller = controller;
    this.itemName = itemName;
  }

  @Override
  public void execute() throws IOException {
    controller.takeItem(itemName);
  }
}