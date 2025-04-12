package controller;

import java.util.List;
import java.util.Objects;

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
  private final GameView view;
  private final List<String> testCommands;

  /**
   * Constructs a BatchController with a game world and a batch file path.
   *
   * @param world         The GameWorld instance
   * @param batchFilePath Path to the batch command file
   * @param view          The GameView to display output
   */
  public BatchController(GameWorld world, String batchFilePath, GameView view) {
    super(world);
    this.batchFilePath = batchFilePath;
    this.view = view;
    this.testCommands = null;
  }

  /**
   * Constructs a BatchController with test commands (for unit testing).
   *
   * @param world        The GameWorld instance
   * @param testCommands List of test commands to execute
   * @param view         The GameView to display output
   */
  public BatchController(GameWorld world, List<String> testCommands, GameView view) {
    super(world);
    this.testCommands = testCommands;
    this.view = view;
    this.batchFilePath = null;
  }

  /**
   * Runs the game using either testCommands or batchFilePath.
   */
  public void run() {
    List<String> commands;

    commands = Objects.requireNonNullElseGet(testCommands, () -> FileIoManager.readFile(batchFilePath));

    for (String line : commands) {
      ParsedCommand parsed = CommandParser.parse(line);
      Command command = CommandFactory.create(parsed.command(), view);
      command.execute();
    }
  }

  private static class Command {
    public void execute() {
      // Default no-op
    }
  }

  private static class CommandFactory {
    /**
     * Creates a command based on the command name.
     *
     * @param commandName Name of the command
     * @param view        GameView for output
     * @return Command instance
     */
    public static Command create(String commandName, GameView view) {
      return switch (commandName.toLowerCase()) {
        case "look" -> new Command() {
          public void execute() {
            view.displayMessage("Executed: look");
          }
        };
        case "inventory" -> new Command() {
          public void execute() {
            view.displayMessage("Executed: inventory");
          }
        };
        case "quit" -> new Command() {
          public void execute() {
            view.displayMessage("Executed: quit");
          }
        };
        default -> new Command() {
          public void execute() {
            view.displayMessage("Invalid command.");
          }
        };
      };
    }
  }
}
