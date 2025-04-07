package controller;

import java.util.List;
import model.GameWorld;
import util.CommandParser;
import util.CommandParser.ParsedCommand;
import util.FileIoManager;
import view.GameView;

/**
 * Controller for running the game in batch mode using a list of predefined commands.
 */
public class BatchController extends GameController {
  private final String batchFilePath;

  /**
   * Constructs a BatchController with a game world and input file.
   *
   * @param world         The GameWorld instance
   * @param batchFilePath Path to the batch command file
   * @param view          The GameView to display output (unused)
   */
  public BatchController(GameWorld world, String batchFilePath, GameView view) {
    super(world);
    this.batchFilePath = batchFilePath;
    // view parameter intentionally unused
  }

  /**
   * Runs the game using the commands from the batch file.
   */
  public void run() {
    List<String> commands = FileIoManager.readFile(batchFilePath);
    for (String line : commands) {
      ParsedCommand parsed = CommandParser.parse(line);
      Command command = CommandFactory.create(parsed.command(), parsed.args());
      command.execute(); // no need for null check — fallback command always returned
    }
  }

  private static class Command {
    public void execute() {
      // Default fallback does nothing
    }
  }

  private static class CommandFactory {
    public static Command create(String commandName, List<String> args) {
      return switch (commandName.toLowerCase()) {
        case "look" -> new Command() {
          @Override
          public void execute() {
            System.out.println("You look around.");
          }
        };
        case "inventory" -> new Command() {
          @Override
          public void execute() {
            System.out.println("You check your inventory.");
          }
        };
        case "quit" -> new Command() {
          @Override
          public void execute() {
            System.out.println("Game quit.");
          }
        };
        default -> new Command() {
          @Override
          public void execute() {
            System.out.println("Invalid command.");
          }
        };
      };
    }
  }
}
