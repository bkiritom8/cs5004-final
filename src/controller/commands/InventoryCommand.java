package controller.commands;

import java.io.IOException;

import controller.Command;
import controller.GameController;

/**
 * Command for displaying the player's inventory.
 */
public class InventoryCommand implements Command {
  private final GameController controller;

  /**
   * Creates a new inventory command.
   *
   * @param controller The controller that will execute the command
   */
  public InventoryCommand(GameController controller) {
    this.controller = controller;
  }

  @Override
  public void execute() throws IOException {
    controller.showInventory();
  }
}