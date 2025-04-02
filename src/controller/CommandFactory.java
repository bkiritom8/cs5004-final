package controller;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import controller.commands.*;
import model.Direction;

/**
 * Factory for creating command objects from input strings.
 * Implements the Factory Pattern to centralize command creation.
 */
public class CommandFactory {

  public static Command createCommand(GameController controller, String commandString) {
    if (commandString == null || commandString.trim().isEmpty()) {
      return new UnknownCommand(controller, "");
    }

    // Parse  command string
    String[] parts = commandString.trim().toLowerCase().split("\\s+", 2);
    String command = parts[0];
    String arguments = parts.length > 1 ? parts[1] : "";

    // Create appropriate command object
    switch (command) {
      case "n":
      case "north":
        return new MoveCommand(controller, Direction.NORTH);

      case "s":
      case "south":
        return new MoveCommand(controller, Direction.SOUTH);

      case "e":
      case "east":
        return new MoveCommand(controller, Direction.EAST);

      case "w":
      case "west":
        return new MoveCommand(controller, Direction.WEST);

      case "l":
      case "look":
        return new LookCommand(controller);

      case "i":
      case "inventory":
        return new InventoryCommand(controller);

      case "t":
      case "take":
        return new TakeCommand(controller, argument);

      case "d":
      case "drop":
        return new DropCommand(controller, argument);

      case "x":
      case "examine":
        return new ExamineCommand(controller, argument);

      case "u":
      case "use":
        return new UseCommand(controller, argument);

      case "a":
      case "answer":
        return new AnswerCommand(controller, argument);

      case "k":
      case "attack":
        return new AttackCommand(controller);

      case "v":
      case "save":
        return new SaveCommand(controller);

      case "r":
      case "restore":
      case "load":
        return new LoadCommand(controller);

      case "q":
      case "quit":
        return new QuitCommand(controller);

      default:
        return new UnknownCommand(controller, commandString);
    }
  }
}
