package controller.commands;

import java.io.IOException;

import controller.Command;
import controller.GameController;

/**
 * Command for attacking a monster.
 */
public class AttackCommand implements Command {
  private final GameController controller;

  /**
   * Creates a new attack command.
   *
   * @param controller The controller that will execute the command
   */
  public AttackCommand(GameController controller) {
    this.controller = controller;
  }

  @Override
  public void execute() throws IOException {
    controller.attackMonster();
  }
}